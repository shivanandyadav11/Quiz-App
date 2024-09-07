# Quiz App

A dynamic and interactive quiz application built with Kotlin for Android, following the MVVM (Model-View-ViewModel) architecture.

## Features

- Interactive quiz interface
- Real-time score tracking
- Multiple choice questions
- Result summary after quiz completion
- Option to restart the quiz

## Architecture

This app is built using the MVVM (Model-View-ViewModel) architecture, which provides a clean separation of concerns:

- **Model**: Represents the data and business logic of the app.
- **View**: The UI layer, implemented using Jetpack Compose.
- **ViewModel**: Acts as a bridge between the Model and View, handling the presentation logic.

## Technologies Used

- Kotlin
- Jetpack Compose
- Coroutines
- Hilt (for dependency injection)
- StateFlow (for reactive programming)

## Project Structure

- `model`: Contains data classes for Quiz, QuizAnswer, and QuizResponseList.
- `service`: Includes QuizRepo and QuizService for data operations.
- `ui.viewModel`: Houses the QuizViewModel, which manages the UI state and business logic.

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the app on an emulator or physical device

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
