# Contributing to CMP App Recipes

We welcome and appreciate your contributions to the CMP App Recipes project! Whether you're fixing bugs, adding new features, improving documentation, or anything in between, your help is invaluable.

## 🤝 Ways to Contribute

There are several ways you can contribute to this project:

* **🐛 Bug Reports:** If you encounter a bug or unexpected behavior, please open a detailed issue on our [GitHub issue tracker](https://github.com/ricarlo-silva/cmp-app-recipes/issues).
* **💡 Feature Requests:** Have a great idea for a new feature or enhancement? Feel free to open an issue to discuss it with the community.
* **🛠️ Pull Requests (PRs):** If you're ready to contribute code directly, submit a pull request with your well-tested changes.
* **📚 Documentation:** Help us make the project more accessible by improving our documentation. Submit pull requests with corrections, clarifications, or new content.
* **🧪 Testing:** Play a crucial role by testing new features, attempting to reproduce reported bugs, and providing valuable feedback.

## 🚀 Getting Started

Ready to jump in? Here's how:

1.  **Fork the Repository:** Click the "Fork" button in the top right corner of the project's GitHub page to create your personal copy of the repository.

2.  **Clone Your Fork:** Clone your forked repository to your local development machine:

    ```bash
    git clone git@github.com:ricarlo-silva/cmp-app-recipes.git
    cd cmp-app-recipes
    ```

3.  **Create a Branch:** Create a dedicated branch for your changes. This keeps your work organized and simplifies the pull request process:

    ```shell
    git checkout -b feature/<your-feature-name>  # For new features
    git checkout -b fix/<your-bug-fix-name>      # For bug fixes
    ```

    *(Replace `<your-feature-name>` or `<your-bug-fix-name>` with a descriptive name for your branch.)*

4.  **Make Your Changes:** Implement your code changes, fix the identified issue, or add the desired new feature.

5.  **Commit Your Changes:** Commit your changes with clear and concise commit messages, adhering to the commit message conventions:

    ```shell
    git add .             # Stage all modified files
    git commit -m "feat(login): Implement user login functionality" # Example commit message
    ```

6.  **Follow Commit Message Conventions:** Use the following prefixes in your commit messages:

    * `feat`: Introducing new features.
    * `fix`: Addressing bug fixes.
    * `docs`: Changes related to documentation.
    * `style`: Updates to code style (formatting, semicolons, etc.).
    * `refactor`: Code refactoring without introducing new features or bug fixes.
    * `test`: Changes related to tests (adding, modifying, or fixing tests).
    * `chore`: Miscellaneous changes to the build process or auxiliary tools.

7.  **Push Your Branch:** Push your local branch to your forked repository on GitHub:

    ```shell
    git push origin <your-branch-name>
    ```

    *(Replace `<your-branch-name>` with the name of your branch.)*

8.  **Open a Pull Request:**
    * Navigate to the original CMP App Recipes repository on GitHub.
    * You should see a prompt to create a pull request from your newly pushed branch.
    * Click "Compare & pull request."
    * Fill out the pull request template (if provided) with a clear and comprehensive description of your changes.
    * Click "Create pull request" to submit your contribution.

## 🎨 Code Style

To maintain a consistent and readable codebase, please adhere to the following style guidelines:

* **Kotlin:** Follow the [official Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html).
* **Naming:** Use descriptive and consistent names for all variables, functions, classes, and other entities.
* **Comments:** Add comments to explain complex logic, non-obvious code sections, or important design decisions.
* **Formatting:** Ensure consistent code formatting and indentation. Utilize Android Studio's built-in code formatting tools (`Code > Reformat Code`).
* **Tests:** All new features and bug fixes **must** include relevant unit and/or integration tests.
* **Run Tests:** Always run all tests locally before submitting a pull request to ensure your changes haven't introduced any regressions.

## Pull Request Guidelines

To ensure a smooth review process, please follow these guidelines when submitting pull requests:

* **Keep it Small and Focused:** Aim for pull requests that address a single issue or implement a single feature. Smaller PRs are easier to review and merge.
* **Provide a Clear Description:** In your pull request, clearly describe the changes you've made, the problem you're addressing (if applicable), and the solution you've implemented.
* **Test Thoroughly:** Ensure your changes are well-tested and do not introduce any new issues.
* **Update Documentation:** If your changes modify the public API or introduce new features, update the relevant documentation accordingly.
* **Address Feedback:** Be prepared to receive feedback on your pull request and be willing to make necessary revisions based on reviewer comments.

## ⚠️ Issue Guidelines

When creating or interacting with issues, please keep the following in mind:

* **Search Before Submitting:** Before opening a new issue, please search the existing issues to avoid duplicates.
* **Use a Descriptive Title:** The title of your issue should clearly and concisely summarize the problem or feature request.
* **Explain the Problem Clearly:** If you're reporting a bug, provide detailed steps to reproduce the issue, the expected behavior, and the actual behavior you observed.
* **Utilize Issue Templates:** If the project provides issue templates, please use them and fill in all the requested information.

## 💬 Communication

* Use the GitHub issue tracker for all bug reports, feature requests, and discussions.
* Be respectful and considerate of others.
* If you have questions or need help, don't hesitate to ask.

## 🛡️ Code of Conduct

We expect all contributors to adhere to our [Code of Conduct](CODE_OF_CONDUCT.md). Be respectful and create a positive environment for everyone.

## 🙏 Thank You!

Thank you for your interest in contributing to CMP App Recipes! Your contributions make this project better.
