/** Represnts a list of musical tracks. The list has a maximum capacity (int),
 *  and an actual size (number of tracks in the list, an int). */
class PlayList {
    private Track[] tracks;  // Array of tracks (Track objects)   
    private int maxSize;     // Maximum number of tracks in the array
    private int size;        // Actual number of tracks in the array

    /** Constructs an empty play list with a maximum number of tracks. */ 
    public PlayList(int maxSize) {
        this.maxSize = maxSize;
        this.tracks = new Track[maxSize];
        this.size = 0;
    }

    /** Returns true if the list is empty, false otherwise. */ 
    public boolean isEmpty() {
        return this.size == 0;
    }

    /** Returns true if the list is full, false otherwise. */ 
    public boolean isFull() {
        return this.size == this.maxSize;
    }

    /** Returns the maximum size of this play list. */ 
    public int getMaxSize() {
        return this.maxSize;
    }
    
    /** Returns the current number of tracks in this play list. */ 
    public int getSize() {
        return this.size;
    }

    /** Method to get a track by index */
    public Track getTrack(int index) {
        if (index >= 0 && index < this.size) {
            return this.tracks[index];
        }
        return null;
    }
    
    /** Appends the given track to the end of this list. 
     *  If the list is full, does nothing and returns false.
     *  Otherwise, appends the track and returns true. */
    public boolean add(Track track) {
        
        if (this.size == this.maxSize) {
            return false;
        }
        else {
            this.size++;
            this.tracks[size - 1] = this.newTrackObject(track);
            return true;
        }

    }

    /** Returns the data of this list, as a string. Each track appears in a separate line. */
    //// For an efficient implementation, use StringBuilder.
    public String toString() {

        String tracksStr = "";
        for (int i = 0; i < this.size; i++) {
            tracksStr += this.tracks[i].toString() + "\n";
        }
        
        if (tracksStr.length() > 0) {
            return tracksStr.substring(0,tracksStr.length() - 1);
        }
        return tracksStr;
    }

    /** Removes the last track from this list. If the list is empty, does nothing. */
     public void removeLast() {
        
        if (this.size > 0) {
            this.tracks[this.size - 1] = null;
            this.size--;
        }

    }
    
    /** Returns the total duration (in seconds) of all the tracks in this list.*/
    public int totalDuration() {
        
        int totalSeconds = 0;
        for (int i = 0; i < this.size; i++) {
            totalSeconds += this.tracks[i].getDuration();
        }

        return totalSeconds;
    }

    /** Returns the index of the track with the given title in this list.
     *  If such a track is not found, returns -1. */
    public int indexOf(String title) {
        if (this.isEmpty()) {
            return -1;
        }
        else {
            for (int i = 0; i < this.size; i++) {
                if (this.tracks[i].getTitle().equals(title)) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**Return a new track with the same values as t */
    public Track newTrackObject(Track t) {
        return new Track(t.getTitle(), t.getArtist(), t.getDuration());
    }

    /** Inserts the given track in index i of this list. For example, if the list is
     *  (t5, t3, t1), then just after add(1,t4) the list becomes (t5, t4, t3, t1).
     *  If the list is the empty list (), then just after add(0,t3) it becomes (t3).
     *  If i is negative or greater than the size of this list, or if the list
     *  is full, does nothing and returns false. Otherwise, inserts the track and
     *  returns true. */
    public boolean add(int i, Track track) {
        if (i < 0 || i >= this.maxSize || this.isFull()) {
            return false;
        }
        else {
            this.size++;

            if (this.isEmpty()) {
                this.tracks[i] = this.newTrackObject(track);
            }
            else {
                Track originalTrack = this.newTrackObject(track);
                Track tFirstIndex = this.tracks[i], tSecondIndex;
                for (int j = i + 1; j < this.size; j++) {
                    tSecondIndex = this.tracks[j];
                    this.tracks[j] = this.newTrackObject(tFirstIndex);
                    tFirstIndex = tSecondIndex;
                }
                this.tracks[i] = this.newTrackObject(originalTrack);
            }

            
            return true;
        }
    }
     
    /** Removes the track in the given index from this list.
     *  If the list is empty, or the given index is negative or too big for this list, 
     *  does nothing*/
    public void remove(int i) {
        if (!this.isEmpty() && i >= 0 && i < this.size) {
            if (i + 1 == this.size) {
                this.tracks[i] = null;
                this.size--;
            }
            else {
                this.tracks[i] = null;
                for (int j = i; j < this.size - 1; j++) {
                    this.tracks[j] = this.newTrackObject(this.tracks[j + 1]);
                }
                this.size--;
            }
        }
    }

    /** Removes the first track that has the given title from this list.
     *  If such a track is not found, or the list is empty, or the given index
     *  is negative or too big for this list, does nothing. */
    public void remove(String title) {
        
        int firstIndex = this.indexOf(title);
        if (firstIndex != -1) {
            this.remove(firstIndex);
        }
    }

    /** Removes the first track from this list. If the list is empty, does nothing. */
    public void removeFirst() {
        this.remove(0);
    }
    
    /** Adds all the tracks in the other list to the end of this list. 
     *  If the total size of both lists is too large, does nothing. */
    //// An elegant and terribly inefficient implementation.
     public void add(PlayList other) {
        if (this.size + other.size <= this.maxSize) {
            for (int i = 0; i < other.getSize(); i++) {
                this.add(other.tracks[i]);
            }
        }
    }

    /** Returns the index in this list of the track that has the shortest duration,
     *  starting the search in location start. For example, if the durations are 
     *  7, 1, 6, 7, 5, 8, 7, then min(2) returns 4, since this the index of the 
     *  minimum value (5) when starting the search from index 2.  
     *  If start is negative or greater than size - 1, returns -1.
     */
    private int minIndex(int start) {

        if (start < 0 || start > this.size - 1) {
            return -1;
        }

        int minDuration = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = start; i < this.size; i++) {
            if (this.tracks[i].getDuration() < minDuration) {
                minDuration = this.tracks[i].getDuration();
                minIndex = i;
            }
        }
        return minIndex;
    }

    /** Returns the title of the shortest track in this list. 
     *  If the list is empty, returns null. */
    public String titleOfShortestTrack() {
        if (this.isEmpty()) {
            return null;
        }
        return tracks[minIndex(0)].getTitle();
    }

    /** Returns the first track. */
    public Track getFirstTrack() {
        return this.newTrackObject(this.tracks[0]);
    }

    /** Sorts this list by increasing duration order: Tracks with shorter
     *  durations will appear first. The sort is done in-place. In other words,
     *  rather than returning a new, sorted playlist, the method sorts
     *  the list on which it was called (this list). */
    public void sortedInPlace() {
        
        Track firstTrack, minTrack;
        int minIndex;
        for (int i = 0; i < this.size; i++) {
            firstTrack = this.newTrackObject(this.tracks[i]);
            minIndex = this.minIndex(i);

            minTrack = this.newTrackObject(this.tracks[minIndex]);
            if (minIndex != i) {
                this.remove(minIndex);
                this.remove(i);
                this.add(i, minTrack);
                this.add(minIndex, firstTrack);
            }
        }

    }
}
