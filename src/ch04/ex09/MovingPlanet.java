/*
 * Copyright (C) 2014 Yoshiki Shibata. All rights reserved.
 */
package ch04.ex09;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Animate a circle, representing a planet, so it travels along an elliptical
 * orbit. Use a PathTransition.
 *
 * 惑星を表す円をアニメーション化して、楕円軌道を回るようにしなさい。 PathTransition を使用します。
 */
public class MovingPlanet extends Application {

    @Override
    public void start(Stage primaryStage) {

        Circle circle = new Circle(110, 10, 10);
        circle.setFill(Color.RED);

        Ellipse ellipse = new Ellipse();
        ellipse.setCenterX(50.0f);
        ellipse.setCenterY(50.0f);
        ellipse.setRadiusX(200.0f);
        ellipse.setRadiusY(50.0f);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(10000));
        pathTransition.setNode(circle);
        pathTransition.setPath(ellipse);
        pathTransition.setCycleCount(Animation.INDEFINITE);
        pathTransition.setInterpolator(Interpolator.LINEAR);

        pathTransition.play();

        BorderPane pane = new BorderPane();
        pane.setCenter(circle);
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
