package flappybird.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Gerenciador de música de fundo do jogo utilizando Java Sound ({@code javax.sound.sampled}).
 *
 * <p><strong>Pilar da POO — Encapsulamento:</strong>
 * O recurso de áudio ({@link Clip}), o estado de mudo e o volume são mantidos privados,
 * expondo apenas operações seguras de controle de reprodução.</p>
 */
public class MusicPlayer {

    public static final String DEFAULT_MUSIC_PATH = "/audio/music.wav";
    private static final float DEFAULT_VOLUME = 0.7f;

    private final String resourcePath;
    private Clip clip;
    private boolean muted;
    private float volume;

    public MusicPlayer() {
        this(DEFAULT_MUSIC_PATH);
    }

    public MusicPlayer(String resourcePath) {
        if (resourcePath == null || resourcePath.isBlank()) {
            throw new IllegalArgumentException("O caminho do recurso de áudio não pode ser vazio.");
        }
        this.resourcePath = resourcePath;
        this.muted = false;
        this.volume = DEFAULT_VOLUME;
    }

    /**
     * Carrega o arquivo de áudio (caso ainda não tenha sido carregado) e inicia a reprodução em loop contínuo.
     */
    public synchronized void playLoop() {
        if (!ensureLoaded()) {
            return;
        }
        if (!muted && !clip.isRunning()) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Pausa a música mantendo a posição atual.
     */
    public synchronized void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Interrompe a música e retorna para o início da faixa.
     */
    public synchronized void stop() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }

    /**
     * Alterna entre mudo e som ativo.
     */
    public synchronized void toggleMute() {
        setMuted(!muted);
    }

    /**
     * Define o estado de mudo da música.
     */
    public synchronized void setMuted(boolean muted) {
        this.muted = muted;
        if (clip == null) {
            return;
        }
        if (muted) {
            if (clip.isRunning()) {
                clip.stop();
            }
        } else {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Define o volume linear entre {@code 0.0f} (silencioso) e {@code 1.0f} (máximo).
     */
    public synchronized void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(1.0f, volume));
        applyVolume();
    }

    /**
     * Libera os recursos de áudio alocados pelo sistema operacional.
     */
    public synchronized void close() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }

    public synchronized boolean isMuted() {
        return muted;
    }

    public synchronized float getVolume() {
        return volume;
    }

    public synchronized boolean isPlaying() {
        return clip != null && clip.isRunning();
    }

    public synchronized boolean isLoaded() {
        return clip != null;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    private boolean ensureLoaded() {
        if (clip != null) {
            return true;
        }
        InputStream rawStream = MusicPlayer.class.getResourceAsStream(resourcePath);
        if (rawStream == null) {
            System.err.println("Recurso de áudio não encontrado: " + resourcePath);
            return false;
        }

        try (BufferedInputStream bufferedStream = new BufferedInputStream(rawStream);
             AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedStream)) {
            Clip loadedClip = AudioSystem.getClip();
            loadedClip.open(audioStream);
            this.clip = loadedClip;
            applyVolume();
            return true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
            // Falha graciosa em ambientes sem dispositivo de áudio (ex.: headless/CI)
            System.err.println("Não foi possível inicializar a música de fundo: " + e.getMessage());
            return false;
        }
    }

    private void applyVolume() {
        if (clip == null || !clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            return;
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float min = gainControl.getMinimum();
        float max = gainControl.getMaximum();
        float dB = (volume <= 0.0001f) ? min : (float) (20.0 * Math.log10(volume));
        gainControl.setValue(Math.max(min, Math.min(max, dB)));
    }
}
