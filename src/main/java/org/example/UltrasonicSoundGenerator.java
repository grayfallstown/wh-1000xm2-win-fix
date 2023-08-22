package org.example;

import javax.sound.sampled.*;

public class UltrasonicSoundGenerator {

    public static void main(String[] args) {
        while (true) {
            try {
                generateUltrasonicSound();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateUltrasonicSound() throws LineUnavailableException {
        float sampleRate = 44100;  // Samples per second
        int frequency = 0;     // Frequency in Hz
        float amplitude = 0.0001f;    // Amplitude (0.0 to 1.0)

        AudioFormat audioFormat = new AudioFormat(sampleRate, 16, 1, true, true);
        SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat);

        line.open(audioFormat);
        line.start();

        byte[] buffer = new byte[(int) sampleRate];
        for (int i = 0; i < buffer.length; i++) {
            double angle = 2.0 * Math.PI * frequency * i / sampleRate;
            short sample = (short) (amplitude * Short.MAX_VALUE * Math.sin(angle));
            buffer[i] = (byte) (sample & 0xFF);
            buffer[i + 1] = (byte) ((sample >> 8) & 0xFF);
            i++;
        }

        line.write(buffer, 0, buffer.length);
        line.drain();
        line.close();
    }
}