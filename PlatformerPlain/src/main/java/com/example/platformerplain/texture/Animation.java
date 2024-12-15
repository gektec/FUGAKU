package com.example.platformerplain.texture;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

public class Animation {

	private Image[] frames;  // 使用 JavaFX 的 Image 类型
	private int currentFrame;
	private int numFrames;

	private int count;
	private int delay;

	private int timesPlayed;

	public Animation() {
		timesPlayed = 0;
	}

	public void setFrames(Image[] frames) {
		this.frames = frames;
		currentFrame = 0;
		count = 0;
		timesPlayed = 0;
		delay = 2;
		numFrames = frames.length;
	}

	public void setDelay(int i) { delay = i; }
	public void setFrame(int i) { currentFrame = i; }
	public void setNumFrames(int i) { numFrames = i; }

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

	public int getFrame() { return currentFrame; }
	public int getCount() { return count; }

	public Image getImage() { return frames[currentFrame]; }
	//overload
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

	public boolean hasPlayedOnce() { return timesPlayed > 0; }
	public boolean hasPlayed(int i) { return timesPlayed == i; }
}
