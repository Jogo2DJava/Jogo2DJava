package flappybird.audio;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MusicPlayerTest {

    @Test
    void defaultMusicResourceExistsAndIsValidWav() throws Exception {
        InputStream stream = MusicPlayer.class.getResourceAsStream(MusicPlayer.DEFAULT_MUSIC_PATH);
        assertNotNull(stream, "O arquivo de música deve existir em src/main/resources/audio/music.wav");

        try (BufferedInputStream buffered = new BufferedInputStream(stream);
             AudioInputStream audioStream = AudioSystem.getAudioInputStream(buffered)) {
            AudioFormat format = audioStream.getFormat();
            assertEquals(AudioFormat.Encoding.PCM_SIGNED, format.getEncoding());
            assertEquals(16, format.getSampleSizeInBits());
            assertTrue(audioStream.getFrameLength() > 0, "O áudio deve conter quadros válidos");
        }
    }

    @Test
    void constructorRejectsNullOrBlankPath() {
        assertThrows(IllegalArgumentException.class, () -> new MusicPlayer(null));
        assertThrows(IllegalArgumentException.class, () -> new MusicPlayer("   "));
    }

    @Test
    void toggleMuteAndVolumeClampWorkCorrectly() {
        MusicPlayer player = new MusicPlayer("/audio/inexistente.wav");

        assertFalse(player.isMuted());
        player.toggleMute();
        assertTrue(player.isMuted());
        player.toggleMute();
        assertFalse(player.isMuted());

        player.setVolume(1.5f);
        assertEquals(1.0f, player.getVolume(), 0.0001f);

        player.setVolume(-0.3f);
        assertEquals(0.0f, player.getVolume(), 0.0001f);

        player.setVolume(0.5f);
        assertEquals(0.5f, player.getVolume(), 0.0001f);
    }

    @Test
    void missingResourceFailsGracefullyWithoutThrowing() {
        MusicPlayer player = new MusicPlayer("/audio/inexistente.wav");
        assertDoesNotThrow(player::playLoop);
        assertFalse(player.isLoaded());
        assertFalse(player.isPlaying());
        assertDoesNotThrow(player::close);
    }
}
