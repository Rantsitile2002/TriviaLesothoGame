package com.example.trivia;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;

public class TriviaGameApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a button to start the trivia game
        Button startButton = new Button("Start Trivia Game");
        startButton.setStyle("-fx-font-size: 18px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px;");

        // Create an ImageView to display the image
        ImageView imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setFitWidth(300);

        // Create a layout pane (BorderPane) and add the start button and image view to it
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.web("#B9E6D8"), null, null))); // Light green background color

        // Load and display the first image
        displayImage(imageView, "horse.jpg");

        root.setCenter(startButton);
        root.setTop(imageView); // Display the image at the top

        // Create a VBox to hold the questions and answer options
        VBox questionsBox = new VBox();
        questionsBox.setSpacing(20);
        questionsBox.setPadding(new Insets(20));
        questionsBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        // Add the questions
        addQuestion(questionsBox, "1. What is the capital of Lesotho?", "horse.jpg",
                "Thaba-Tseka", "Polihali", "Mokhotlong", "Maseru");
        addQuestion(questionsBox, "2. Which is the largest dam in Lesotho?", "donkey.jpg",
                "Katse dam", "Muela dam", "Mohale dam", "Mphorosane");
        addQuestion(questionsBox, "3. Who is the founder of Basotho Nation?", "katsedam.jpg",
                "King Letsie 111", "King Moshoeshoe 1", "Sam Matekane", "King Lerothi");
        addQuestion(questionsBox, "4. Where was the Basotho Fortress?", "mahleu.jpg",
                "Thaba-Bosiu", "Thabana-ntlenyane", "Thabana-li-mmele", "Thab-Putsoa");
        addQuestion(questionsBox, "5. What beer is used at the Basotho rituals?", "moshoeshoe.jpg",
                "Mahleu", "Mohlaba", "Seqhaqhabola", "Setono");
        addQuestion(questionsBox, "6. What did Basotho use for transport?", "giloane.jpg",
                "Cows", "Sheep", "Donkey", "Horse");
        addQuestion(questionsBox, "7. Who is the current prime minister of Lesotho?", "horse.jpg",
                "Dr Metsing Mothetjoa", "Dr Pakalitha Mosisili", "Dr Samuel Matekane", "Dr Mhao Salemane");

        // Create a ScrollPane and add the VBox containing questions to it
        ScrollPane scrollPane = new ScrollPane(questionsBox);
        scrollPane.setFitToWidth(true); // Allow horizontal scrolling
        scrollPane.setFitToHeight(true); // Allow vertical scrolling

        // Add the ScrollPane to the left side of the BorderPane
        root.setLeft(scrollPane);

        // Create a Scene with the layout pane as its root node
        Scene scene = new Scene(root, 800, 600); // Adjust width and height as needed

        // Set the title of the stage (window)
        primaryStage.setTitle("Lesotho Trivia Game");

        // Set the scene for the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();

        // Event handler for the start button
        startButton.setOnAction(event -> {
            // Create and start the trivia game
            TriviaGame triviaGame = new TriviaGame(primaryStage);
            triviaGame.start();
            // Play background audio
            playBackgroundAudio("taa.mp3");
            // Display video at the end of the game
            displayEndVideo();
        });
    }

    private void displayImage(ImageView imageView, String imageName) {
        // Load the image from the images folder
        File imageFile = new File("src/images/" + imageName);
        Image image = new Image(imageFile.toURI().toString());
        // Set the image to the ImageView
        imageView.setImage(image);
    }

    private void addQuestion(VBox questionsBox, String question, String imageName,
                             String option1, String option2, String option3, String option4) {
        // Create the question label
        Label questionLabel = new Label(question);
        questionLabel.setFont(Font.font("Arial", 18));
        questionLabel.setTextFill(Color.web("#333333")); // Dark gray text color

        // Create the radio buttons for options
        RadioButton qOption1 = createRadioButton(option1);
        RadioButton qOption2 = createRadioButton(option2);
        RadioButton qOption3 = createRadioButton(option3);
        RadioButton qOption4 = createRadioButton(option4);

        // Group the radio buttons
        ToggleGroup toggleGroup = new ToggleGroup();
        qOption1.setToggleGroup(toggleGroup);
        qOption2.setToggleGroup(toggleGroup);
        qOption3.setToggleGroup(toggleGroup);
        qOption4.setToggleGroup(toggleGroup);

        // Load and display the image on the right side
        ImageView questionImage = new ImageView();
        displayImage(questionImage, imageName);

        // Add components to the VBox
        VBox questionBox = new VBox(10);
        questionBox.setAlignment(Pos.CENTER_LEFT);
        questionBox.getChildren().addAll(questionLabel, qOption1, qOption2, qOption3, qOption4, questionImage);

        // Add the VBox to the main VBox
        questionsBox.getChildren().add(questionBox);
    }

    private RadioButton createRadioButton(String text) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setFont(Font.font("Arial", 14));
        radioButton.setTextFill(Color.web("#333333")); // Dark gray text color
        return radioButton;
    }

    private void playBackgroundAudio(String audioFileName) {
        String mediaFile = "src/videos/" + audioFileName;
        Media media = new Media(new File(mediaFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    private void displayEndVideo() {
        // Create a Media object to represent the video file
        Media media = new Media(new File("src/we.mp4").toURI().toString());
        // Create a MediaPlayer to play the media
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        // Create a MediaView to display the media player
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitWidth(400);
        mediaView.setFitHeight(300);

        // Create a VBox to hold the mediaView
        VBox endVideoBox = new VBox();
        endVideoBox.setAlignment(Pos.CENTER);
        endVideoBox.getChildren().add(mediaView);

        // Create a new Scene for the video
        Scene videoScene = new Scene(endVideoBox, 800, 600); // Adjust width and height as needed

        // Create a new Stage for the video
        Stage videoStage = new Stage();
        videoStage.setScene(videoScene);
        videoStage.setTitle("End Video");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
