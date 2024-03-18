import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

public class BacktrackingBeamSearch {

    private List<Board> currentLayer = new ArrayList<>();
    private Set<Board> considered = new HashSet<>();
    private List<Board> nextLayer = new ArrayList<>();
    private List<Board> extraNodes = new ArrayList<>();

    private int winNum;

    private int beamWidth;

    public BacktrackingBeamSearch (Board initialState, int winNum, int beamWidth) {
        currentLayer.add(initialState);
        considered.add(initialState);
        this.winNum = winNum;
        this.beamWidth = beamWidth;
    }
    public Board search() {
        Board solution = null;
        while ((solution == null) && ((!currentLayer.isEmpty() || !extraNodes.isEmpty()))) {
            //System.out.println("Expanding...");
            solution = expandLayer();
        }
        return solution;
    }

    private Board expandLayer() {
        if (currentLayer.isEmpty()) {
            List<Board> toRemove = new ArrayList<>();
            for (int i = 0; i < beamWidth; i++) {
                if (!(extraNodes.get(i) == null)) {
                    currentLayer.add(extraNodes.get(i));
                    toRemove.add(extraNodes.get(i));
                }
                for (int j = 0; j < toRemove.size(); j++) {
                    extraNodes.remove(toRemove.get(i));
                }
            }
        }
        for (Board board : currentLayer) {
            expandNode(board);
        }
        currentLayer = new ArrayList<>(nextLayer);
        nextLayer = new ArrayList<>();
        List<Board> candidates = new ArrayList<>();
        for (Board candidate : currentLayer) {
            if (candidate.isGoalState(winNum)) {
                candidates.add(candidate);
                //System.out.println("Candidate score: " + candidate.getScore());
            }
        }
        float bestScore = MIN_VALUE;
        Board bestBoard = null;
        for (Board candidate : candidates) {
            if (candidate.getF() > bestScore) {
                bestBoard = candidate;
                bestScore = bestBoard.getF();
            }
        }
        return bestBoard;
    }

    private void expandNode(Board board) throws NullPointerException {
        List<Board> children = board.getSuccessors();
        for (Board child : children) {
            // line 3-5 of pseudocode
            if ((nextLayer.size() < beamWidth) && (!considered.contains(child))) {
                //System.out.println("expandNode: case 1");
                nextLayer.add(child);
                considered.add(child);
            }
            // line 6-14
            else if ((nextLayer.size() == beamWidth) && (!considered.contains(child))) {
                //System.out.println("expandNode: case 2");
                considered.add(child);
                if (child.getF() > getWorst().getF()) {
                    //System.out.println("expandNode: case 2a");
                    Board removed = getWorst();
                    nextLayer.remove(removed);
                    extraNodes.add(removed);
                    nextLayer.add(child);
                } else {
                    //System.out.println("expandNode: case 2b");
                    extraNodes.add(child);
                }
            }
            // lines 15-20
            else if ((nextLayer.size() < beamWidth) && considered.contains(child)) {
                //System.out.println("expandNode: case 3");
                Board incumbent = null;
                for (Board considered : considered) {
                    if (considered.equals(child)) {
                        incumbent = considered;
                    }
                }
                assert incumbent != null;
                if (child.getF() > incumbent.getF()) {
                    //System.out.println("expandNode: case 3a");
                    considered.remove(incumbent);
                    considered.add(child);
                    int removeIndex = -1;
                    for (int i = 0; i < nextLayer.size(); i++) {
                        if (nextLayer.get(i).equals(incumbent)) {
                            removeIndex = i;
                        }
                    }
                    if (removeIndex > -1) {
                        nextLayer.remove(removeIndex);
                    }
                    nextLayer.add(child);
                }
            }
            // lines 21-34
            else if ((nextLayer.size() == beamWidth) && considered.contains(child)) {
                //System.out.println("expandNode: case 4");
                Board incumbent = null;
                for (Board considered : considered) {
                    if (considered.equals(child)) {
                        incumbent = considered;
                    }
                }
                assert incumbent != null;
                if ((child.getF() > incumbent.getF()) && nextLayer.contains(incumbent)) {
                    //System.out.println("expandNode: case 4a");
                    considered.remove(incumbent);
                    considered.add(child);
                    int removeIndex = -1;
                    for (int i = 0; i < nextLayer.size(); i++) {
                        if (nextLayer.get(i).equals(incumbent)) {
                            removeIndex = i;
                        }
                    }
                    if (removeIndex > -1) {
                        nextLayer.remove(removeIndex);
                    }
                    nextLayer.add(child);
                }
                // lines 27-31
                else if (child.getF() > getWorst().getF()) {
                    //System.out.println("expandNode: case 4b");
                    considered.remove(incumbent);
                    considered.add(child);
                    Board removed = getWorst();
                    nextLayer.remove(removed);
                    extraNodes.add(removed);
                }
                // lines 32-34
                else if (child.getF() < getWorst().getF()) {
                    //System.out.println("expandNode: case 4c");
                    considered.remove(incumbent);
                    considered.add(child);
                    extraNodes.remove(incumbent);
                    extraNodes.add(child);
                }
            }
        }
    }

    private Board getWorst() {
        float value = MAX_VALUE;
        Board worst = null;
        for (Board child : nextLayer) {
            if (child.getF() < value) {
                worst = child;
                value = worst.getF();
            }
        }
        return worst;
    }
}
