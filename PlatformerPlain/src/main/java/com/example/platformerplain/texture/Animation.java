package com.example.platformerplain.texture;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

/**
 * This class manages a sequence of images (frames) to create an animation effect.
 * It handles frame updates based on a specified delay and allows for flipping image.
 *
 * @author Changyu Li
 * @date 2024/12/15
 */
public class Animation {

	private Image[] frames;
	private int currentFrame;
	private int numFrames;

	private int count;
	private int delay;

	private int timesPlayed;

	/**
	 * Constructs an Animation instance.
	 * Initializes the timesPlayed to 0.
	 */
	public Animation() {
		timesPlayed = 0;
	}

	/**
	 * Sets the frames for the animation.
	 *
	 * @param frames an array of Image objects representing the frames of the animation
	 */
	public void setFrames(Image[] frames) {
		this.frames = frames;
		currentFrame = 0;
		count = 0;
		timesPlayed = 0;
		delay = 2;
		numFrames = frames.length;
	}

	/**
	 * Sets the delay between frame updates.
	 *
	 * @param i the delay in frames
	 */
	public void setDelay(int i) {
		delay = i;
	}

	/**
	 * Updates the animation state by advancing to the next frame if the delay has been reached.
	 */
	public void update() {
		if (delay == -1) return;

		count++;

		if (count >= delay) {
			currentFrame++;
			count = 0;
		}
		if (currentFrame == numFrames) {
			currentFrame = 0;
			timesPlayed++;
		}
	}

	/**
	 * Returns the current frame as an Image.
	 *
	 * @return the current Image frame
	 */
	public Image getImage() {
		return frames[currentFrame];
	}

	/**
	 * Returns the current frame as an Image, optionally flipping it horizontally.
	 *
	 * @param faceLeft if true, the image will be flipped to face left
	 * @return the current Image frame, possibly flipped
	 */
	public Image getImage(boolean faceLeft) {
		Image image = frames[currentFrame];
		if (faceLeft) {
			ImageView imageView = new ImageView(image);
			imageView.getTransforms().add(new Scale(-1, 1, image.getWidth() / 2, image.getHeight() / 2));
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
			return imageView.snapshot(params, null);
		}
		return image;
	}

	/**
	 * Checks if the animation has played at least once.
	 *
	 * @return true if the animation has played at least once, false otherwise
	 */
	public boolean hasPlayedOnce() {
		return timesPlayed > 0;
	}
}
