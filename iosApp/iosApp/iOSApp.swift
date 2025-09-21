import FirebaseCore
import FirebaseCrashlytics
import FirebaseMessaging
import SwiftUI
import UserNotifications
import shared

class AppDelegate: UIResponder, UIApplicationDelegate {

    private lazy var fcmHandler: IFcmHandler = injectLazy()()
    private lazy var deepLinkHandler: IDeepLinkHandler = injectLazy()()
    private lazy var crashlytics: CrashlyticsLogger = injectLazy()()
    private lazy var remoteConfig: RemoteConfigProvider = injectLazy()()

    private let gcmMessageIDKey = "gcm.message_id"

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication
            .LaunchOptionsKey: Any]?
    ) -> Bool {
        FirebaseApp.configure()
        Crashlytics.crashlytics().setCrashlyticsCollectionEnabled(true)
        remoteConfig.fetchAndActivate()

        // [START set_messaging_delegate]
        Messaging.messaging().delegate = self
        // [END set_messaging_delegate]

        // Register for remote notifications. This shows a permission dialog on first run, to
        // show the dialog at a more appropriate time move this registration accordingly.
        // [START register_for_notifications]

        UNUserNotificationCenter.current().delegate = self

        //         let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
        //         UNUserNotificationCenter.current().requestAuthorization(
        //             options: authOptions,
        //             completionHandler: { _, _ in }
        //         )

        application.registerForRemoteNotifications()

        // [END register_for_notifications]

        return true
    }

    func application(
        _ application: UIApplication,
        didReceiveRemoteNotification userInfo: [AnyHashable: Any]
    ) {
        // If you are receiving a notification message while your app is in the background,
        // this callback will not be fired till the user taps on the notification launching the application.
        // TODO: Handle data of notification
        fcmHandler.onMessageReceived(remoteMessage: convertUserInfo(userInfo))

        // With swizzling disabled you must let Messaging know about the message, for Analytics
        // Messaging.messaging().appDidReceiveMessage(userInfo)

        // Print message ID.
        if let messageID = userInfo[gcmMessageIDKey] {
            crashlytics.log(message: "Message ID: \(messageID)")
        }

        // Print full message.
        crashlytics.log(message: "User \(userInfo)")
    }

    // [START receive_message]
    func application(
        _ application: UIApplication,
        didReceiveRemoteNotification userInfo: [AnyHashable: Any]
    ) async
        -> UIBackgroundFetchResult
    {
        // If you are receiving a notification message while your app is in the background,
        // this callback will not be fired till the user taps on the notification launching the application.
        // TODO: Handle data of notification
        fcmHandler.onMessageReceived(remoteMessage: convertUserInfo(userInfo))

        // With swizzling disabled you must let Messaging know about the message, for Analytics
        // Messaging.messaging().appDidReceiveMessage(userInfo)

        // Print message ID.
        if let messageID = userInfo[gcmMessageIDKey] {
            crashlytics.log(message: "Message ID: \(messageID)")
        }

        // Print full message.
        crashlytics.log(message: "User \(userInfo)")

        return UIBackgroundFetchResult.newData
    }

    // [END receive_message]

    func application(
        _ application: UIApplication,
        didFailToRegisterForRemoteNotificationsWithError error: Error
    ) {
        crashlytics.log(
            message: "Unable to register for remote notifications: \(error.localizedDescription)"
        )
    }

    // This function is added here only for debugging purposes, and can be removed if swizzling is enabled.
    // If swizzling is disabled then this function must be implemented so that the APNs token can be paired to
    // the FCM registration token.
    func application(
        _ application: UIApplication,
        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
    ) {
        crashlytics.log(message: "APNs token retrieved: \(deviceToken)")

        // With swizzling disabled you must set the APNs token here.
        // Messaging.messaging().apnsToken = deviceToken
    }

    // Handles deep links when the app is launched by a URL
    func application(
        _ app: UIApplication,
        open url: URL,
        options: [UIApplication.OpenURLOptionsKey: Any] = [:]
    ) -> Bool {
        handleDeepLink(url: url)
        return true
    }

    // Handles deep links while the app is in the foreground or background (optional)
    func application(
        _ application: UIApplication,
        continue userActivity: NSUserActivity,
        restorationHandler: @escaping ([UIUserActivityRestoring]?) -> Void
    ) -> Bool {
        if userActivity.activityType == NSUserActivityTypeBrowsingWeb,
            let url = userActivity.webpageURL
        {
            handleDeepLink(url: url)
            return true
        }
        return false
    }

    private func handleDeepLink(url: URL) {
        deepLinkHandler.processDeepLink(uri: url.absoluteString)
    }
}

// [START ios_10_message_handling]

extension AppDelegate: UNUserNotificationCenterDelegate {
    // Receive displayed notifications for iOS 10 devices.
    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification
    ) async
        -> UNNotificationPresentationOptions
    {
        let userInfo = notification.request.content.userInfo

        // With swizzling disabled you must let Messaging know about the message, for Analytics
        // Messaging.messaging().appDidReceiveMessage(userInfo)

        // [START_EXCLUDE]
        // Print message ID.
        if let messageID = userInfo[gcmMessageIDKey] {
            crashlytics.log(message: "Message ID: \(messageID)")
        }
        // [END_EXCLUDE]

        // Print full message.
        crashlytics.log(message: "User \(userInfo)")

        // Change this to your preferred presentation option
        return [[.alert, .sound]]
    }

    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        didReceive response: UNNotificationResponse
    ) async {
        let userInfo = response.notification.request.content.userInfo

        // [START_EXCLUDE]
        // Print message ID.
        if let messageID = userInfo[gcmMessageIDKey] {
            crashlytics.log(message: "Message ID: \(messageID)")
        }
        // [END_EXCLUDE]

        fcmHandler.onClickMessage(remoteMessage: convertUserInfo(userInfo))

        // With swizzling disabled you must let Messaging know about the message, for Analytics
        // Messaging.messaging().appDidReceiveMessage(userInfo)

        // Print full message.
        crashlytics.log(message: "User \(userInfo)")
    }
}

func convertUserInfo(_ userInfo: [AnyHashable: Any]) -> [String: Any] {
    var stringDict: [String: Any] = [:]

    for (key, value) in userInfo {
        guard let stringKey = key as? String else { continue }
        stringDict[stringKey] = value
    }

    return stringDict
}

// [END ios_10_message_handling]

extension AppDelegate: MessagingDelegate {
    // [START refresh_token]
    func messaging(
        _ messaging: Messaging,
        didReceiveRegistrationToken fcmToken: String?
    ) {
        crashlytics.log(message: "Firebase registration token: \(String(describing: fcmToken))")

        let dataDict: [String: String] = ["token": fcmToken ?? ""]
        NotificationCenter.default.post(
            name: Notification.Name("FCMToken"),
            object: nil,
            userInfo: dataDict
        )
        // TODO: If necessary send token to application server.
        // Note: This callback is fired at each app startup and whenever a new token is generated.
        fcmHandler.onNewToken(token: String(describing: fcmToken))
    }

    // [END refresh_token]
}

@main
struct iOSApp: App {

    init() {
        InitKoinKt.doInitKoin(
            includeModule: CommonModule_iosKt.createIosModule(
                crashlytics: CrashlyticsProviderImpl(),
                remoteConfig: RemoteConfigProviderImpl()
            ),
            config: nil
        )
    }

    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
