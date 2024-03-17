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

    private final int beamWidth = 5;

    public BacktrackingBeamSearch (Board initialState) {
        currentLayer.add(initialState);
        considered.add(initialState);
    }
    public Board search(int beamWidth) {
        Board solution = null;
        while ((solution == null) && ((!currentLayer.isEmpty() || !extraNodes.isEmpty()))) {
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
            if (candidate.isGoal()) {
                candidates.add(candidate);
            }
        }
        float bestScore = MIN_VALUE;
        Board bestBoard = null;
        for (Board candidate : candidates) {
            if (candidate.getValue() > bestScore) {
                bestBoard = candidate;
                bestScore = bestBoard.getValue();
            }
        }
        return bestBoard;
    }

    private void expandNode(Board board) {
        List<Board> children = board.getSuccessors();
        for (Board child : children) {
            // line 3-5 of pseudocode
            if ((nextLayer.size() < beamWidth) && (!considered.contains(child))) {
                nextLayer.add(child);
                considered.add(child);
            }
            // line 6-14
            else if ((nextLayer.size() == beamWidth) && (!considered.contains(child))) {
                considered.add(child);
                if (child.getValue() > getWorst().getValue()) {
                    Board removed = getWorst();
                    nextLayer.remove(removed);
                    extraNodes.add(removed);
                    nextLayer.add(child);
                } else {
                    extraNodes.add(child);
                }
            }
            // lines 15-20
            else if ((nextLayer.size() < beamWidth) && considered.contains(child)) {
                Board incumbent = null;
                for (Board considered : considered) {
                    if (considered.equals(child)) {
                        incumbent = considered;
                    }
                }
                assert incumbent != null;
                if (child.getValue() > incumbent.getValue()) {
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
                Board incumbent = null;
                for (Board considered : considered) {
                    if (considered.equals(child)) {
                        incumbent = considered;
                    }
                }
                assert incumbent != null;
                if ((child.getValue() > incumbent.getValue()) && nextLayer.contains(incumbent)) {
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
                else if (child.getValue() > getWorst().getValue()) {
                    considered.remove(incumbent);
                    considered.add(child);
                    Board removed = getWorst();
                    nextLayer.remove(removed);
                    extraNodes.add(removed);
                }
                // lines 32-34
                else if (child.getValue() < getWorst().getValue()) {
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
            if (child.getValue() < value) {
                worst = child;
                value = worst.getValue();
            }
        }
        return worst;
    }

}
