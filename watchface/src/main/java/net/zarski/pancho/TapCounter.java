package net.zarski.pancho;

class TapCounter {
    private int i;
    private ActionListener actionListener;
    private long firstClick;
    private int max;

    public TapCounter(int max) {
        this.max = max;
        this.i = 0;
    }

    public void tap(int tapType, int x, int y, long eventTime){
        i++;
        if (((System.currentTimeMillis() - firstClick) / 1000) < 10) {
            if (i >= max) {
                actionListener.onActivation(tapType, x,y,eventTime);
                reset();
            }
        } else {
            i = 1;
            firstClick = System.currentTimeMillis();
        }
    }

    private void reset() {
        i =0;
    }

    public void setListener(ActionListener actionListener){
        this.actionListener = actionListener;
    }

    public interface ActionListener{
        void onActivation(int tapType, int x, int y, long eventTime);
    }

}
