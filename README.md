# Quiz App

A dynamic and interactive quiz application built with Kotlin for Android, following the MVVM (Model-View-ViewModel) architecture.

## Features

- Interactive quiz interface
- Real-time score tracking
- Multiple choice questions
- Result summary after quiz completion
- Option to restart the quiz

## Screenshots

Here are some screenshots showcasing the main features of the Quiz App:

### Landing Screen

||
|:---:|
|<img width="300" alt="home" src="https://github.com/user-attachments/assets/3a855343-f0df-410e-9236-39a128012dea">|
*The landing page where users can start a new quiz.*

### Question Screen

|||
|:---:|:---:|
|<img width="300" alt="ques12" src="https://github.com/user-attachments/assets/2b1c303a-6aa9-43d4-b66b-8904151685d5">|<img width="300" alt="ques1" src="https://github.com/user-attachments/assets/df573528-a417-4c64-a7ce-ebf015c6b850">|
*An example of a quiz question with multiple choice options.*

### Results Screen
||
|:---:|
|<img width="300" alt="result" src="https://github.com/user-attachments/assets/31d3502d-fb93-486d-831d-ac6edc154797">|
*The final screen showing the user's score and performance summary.*

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
