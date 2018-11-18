package ours;

public class Model {
    Index mIndex;
    Dictionary mDictionary;

    public Model(Index i, Dictionary d){
        this.mIndex = i;
        this.mDictionary = d;
    }

    public Dictionary getmDictionary() {
        return mDictionary;
    }

    public Index getmIndex() {
        return mIndex;
    }

    public void setmIndex(Index mIndex) {
        this.mIndex = mIndex;
    }

    public void setmDictionary(Dictionary mDictionary) {
        this.mDictionary = mDictionary;
    }
}