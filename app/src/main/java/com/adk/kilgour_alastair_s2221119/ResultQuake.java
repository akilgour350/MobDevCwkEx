package com.adk.kilgour_alastair_s2221119;

public class ResultQuake {
    private Earthquake quake;
    private Resemblance resemblance;

    public ResultQuake(Earthquake eq, Resemblance r) {
        quake = eq;
        resemblance = r;
    }

    public void setQuake(Earthquake eq) { quake = eq; }
    public void setResemblance(Resemblance r) { resemblance = r; }
    public Earthquake getQuake() { return quake; }
    public Resemblance getResemblance() { return resemblance; }
}

enum Resemblance {
    Exact, Near
}
