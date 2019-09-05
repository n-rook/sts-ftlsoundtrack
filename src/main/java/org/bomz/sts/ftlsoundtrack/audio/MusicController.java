package org.bomz.sts.ftlsoundtrack.audio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static basemod.DevConsole.logger;

/**
 * Allows for playing music, stopping it, etc.
 *
 * Note!
 *
 * The way Slay the Spire actually works is there's a "Music Master" class that does all this stuff.
 * To be frank, don't steal this class if you want to play custom music, instead just hook into
 * MusicMaster.
 *
 * But, if you want to do the awesome dual fade thing, you have to use this class.
 *
 * Notes on FTL's soundtrack:
 *
 * When "new" songs come in, they just play after a short moment of silence.
 */
public class MusicController {

  private static MusicController _instance;
  private MusicBeingPlayed singleBGM;
  private SongPlaylist playlist;

  /**
   * Get the singleton instance.
   */
  public static synchronized MusicController instance() {
    if (_instance == null) {
      _instance = new MusicController(MusicSupplier.getInstance());
    }
    return _instance;
  }

  private final MusicSupplier supplier;

  public MusicController(MusicSupplier supplier) {
    this.supplier = supplier;
  }

  /**
   * Play a single song, in both "intense" and "relaxed" settings.
   */
  public void playSingleBGM(MusicSupplier.Song song, boolean shouldLoop) {
    MusicBeingPlayed newAudio = MusicBeingPlayed.single(supplier.get(song));
    newAudio.setLooping(shouldLoop);
    this.stopExistingAudio();
    this.singleBGM = newAudio;
    this.singleBGM.start();
  }

  /**
   * Play a queue of music.
   *
   * When each piece ends, we switch over to a new song in the list.
   *
   * We always begin by playing the first song in the list.
   */
  public void playBGMQueue(List<MusicSupplier.SongPair> playlist, MusicMode mode) {
    // TODO: Reset the list when over
    SongPlaylist songPlaylist = create(playlist);
    this.stopExistingAudio();
    this.playlist = songPlaylist;
    this.playlist.start(mode);
  }

  private SongPlaylist create(List<MusicSupplier.SongPair> playlist) {
    if (playlist.size() < 2) {
      throw new IllegalArgumentException("We do not support playlists with length < 2");
    }

    // First, we shuffle the playlist.
    ArrayList<MusicSupplier.SongPair> shuffle = shuffle(playlist, true);

    // Next, we load the first and second songs.
    MusicBeingPlayed first = MusicBeingPlayed.joint(supplier.get(shuffle.get(0)));
    MusicBeingPlayed second = MusicBeingPlayed.joint(supplier.get(shuffle.get(1)));

    // Now, we create the queue by snipping them off.
    ArrayList<MusicSupplier.SongPair> queue = new ArrayList<>(shuffle.subList(2, shuffle.size()));

    // Finally, we construct the object!
    return new SongPlaylist(supplier, first, second, queue, new ArrayList<>(playlist));
  }

  private static <T> ArrayList<T> shuffle(List<T> input, boolean keepFirstItem) {
    if (input.isEmpty()) {
      return new ArrayList<>();
    }

    if (keepFirstItem) {
      ArrayList<T> newList = new ArrayList<>();
      newList.add(input.get(0));
      newList.addAll(shuffle(input.subList(1, input.size()), false));
      return newList;
    }

    ArrayList<T> newList = new ArrayList<>(input);
    Collections.shuffle(newList);
    return newList;
  }

  /**
   * Handles a list of songs which play in random order.
   */
  private static class SongPlaylist {
    // Current song (as "MusicBeingPlayed")
    // Up next (as "MusicBeingPlayed")
    // Remainder (as a list of SongPairs)
    // Entire list (as a list of SongPairs)

    private final MusicSupplier supplier;
    // The song currently being played.
    private MusicBeingPlayed currentAudio;
    // The song that will be played next.
    private MusicBeingPlayed upNext;
    // The rest of the songs that will be played, in order.
    private ArrayList<MusicSupplier.SongPair> queue;
    // All songs that are a part of this playlist. This exists because the playlist will repeat
    // in a different order after all songs are played.
    private final ArrayList<MusicSupplier.SongPair> fullPlaylist;

    public SongPlaylist(MusicSupplier supplier, MusicBeingPlayed first, MusicBeingPlayed second, ArrayList<MusicSupplier.SongPair> queue, ArrayList<MusicSupplier.SongPair> fullPlaylist) {
      this.supplier = supplier;
      this.currentAudio = first;
      this.upNext = second;
      this.queue = queue;
      this.fullPlaylist = fullPlaylist;
    }

    public void start(MusicMode mode) {
      startCurrentAudio(mode);
    }

    private void startCurrentAudio(MusicMode mode) {
      this.currentAudio.start(mode);
      this.currentAudio.setOnCompletionListener(music -> this.onCompletion());
    }

    /**
     * Stop playback.
     */
    public void stop() {
      this.currentAudio.stop();
      // Now the onCompletion handler will never run...... right?
    }

    /**
     * Runs every frame so we do fades and stuff.
     */
    public void update() {
      this.currentAudio.update();
    }

    // TODO: dispose

    private void onCompletion() {
      logger.info("Running onCompletion handler for audio");
      // TODO: dispose of currentAudio

      MusicBeingPlayed previousAudio = this.currentAudio;
      this.currentAudio = upNext;
      startCurrentAudio(previousAudio.getCurrentMode());
      previousAudio.dispose();

      if (this.queue.isEmpty()) {
        this.refillQueue();
      }

      MusicSupplier.SongPair nextInQueue = this.queue.remove(0);
      this.upNext = MusicBeingPlayed.joint(supplier.get(nextInQueue));
    }

    private void refillQueue() {
      this.queue.clear();

      // TODO: It would be nice to prohibit repeated songs.
      ArrayList<MusicSupplier.SongPair> newQueue = new ArrayList<>(this.fullPlaylist);
      Collections.shuffle(newQueue);
      this.queue.addAll(newQueue);
    }

    /**
     * Switch the mode of the playlist.
     */
    public void switchMode(MusicMode newMode) {
      this.currentAudio.switchMode(newMode);
    }
  }

  /**
   * Called every frame.
   */
  public void update() {
    if (this.singleBGM != null) {
      this.singleBGM.update();
    }
    if (this.playlist != null) {
      this.playlist.update();
    }
  }

  private void stopExistingAudio() {
    // TODO: Figure out how to fade out gracefully here
    if (this.singleBGM != null) {
      this.singleBGM.stop();
      this.singleBGM = null;
    }
    if (this.playlist != null) {
      this.playlist.stop();
      this.playlist = null;
    }
  }

  /**
   * Switch from a "relaxed" to an "intense" setting.
   */
  public void setMode(MusicMode newMode) {
    if (this.singleBGM != null) {
      this.singleBGM.switchMode(newMode);
    }
    if (this.playlist != null) {
      this.playlist.switchMode(newMode);
    }
  }

  public enum MusicMode {
    RELAXED,
    INTENSE
  }
}
