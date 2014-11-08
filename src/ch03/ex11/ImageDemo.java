/*
 * Copyright (C) 2014 Yoshiki Shibata. All rights reserved.
 */
package ch03.ex11;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ImageDemo extends Application {

    public static Image transform(Image in, ColorTransformer f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(
                width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(x, y,
                        f.apply(x, y, in.getPixelReader().getColor(x, y)));
            }
        }
        return out;
    }

    @Override
    public void start(Stage stage) {
        Image image = new Image("eiffel-tower.jpg");

        ColorTransformer brighter = ColorTransformer.toColorTransformer(Color::brighter);
        ColorTransformer framed = (x, y, c) -> x < 10 || x > image.getWidth() - 10
                || y < 10 || y > image.getHeight() - 10 ? Color.GRAY : c;

        ColorTransformer composed = ColorTransformer.compose(brighter, framed);
        Image image2 = transform(image, composed);

        stage.setScene(new Scene(new HBox(
                new ImageView(image),
                new ImageView(image2))));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
