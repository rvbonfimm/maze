package Classes;

import Search.*;
import Search.Search;
import java.util.Stack;

public class Robo {

    /**
     * Recebe um Board Faz uma busca em board recebe um caminho armazena o
     * caminho
     *
     * OBJETIVO: percorrer um caminho - nó por nó ate o final
     */
    private Board _board;
    private boolean _walk;
    private Node _current;
    private Stack<Node> _path;
    private int _cost;

    public Robo(Board board) {
        _board = board;
        _walk = false;
        _current = null;
        _cost = 0;
    }

    private void setPath(Node path) {
        _path = new Stack<>();
        while (path.getParent() != null) {
            _cost += path.getCost();
            _path.push(path);
            path = path.getParent();
        }
    }

    public void searchByDFS() throws Search.NoSuchPathException {
        if (_walk == true) {
            return;// se esta andando entao nao é possivel buscar caminhos
        }
        Search s = new Dfs(_board);
        Node p = s.run();

        setPath(p);
        next();

    }

    public void searchByAs() throws Search.NoSuchPathException {
        if (_walk == true) {
            return;
        }

        Heuristic.manhattan(_board);
        Search s = new As(_board);
        Node p = s.run();

        setPath(p);
        next();
    }

    public void searchByGreedy() throws Search.NoSuchPathException {
        if (_walk == true) {
            return;
        }

        Heuristic.manhattan(_board);
        Search s = new Greedy(_board);
        Node p = s.run();

        setPath(p);
        next();
    }

    public void searchByCustoUniforme() throws Search.NoSuchPathException {
        if (_walk == true) {
            return;// se esta andando entao nao é possivel buscar caminhos
        }
        Search s = new CustoUniforme(_board);
        Node p = s.run();

        setPath(p);
        next();

    }

    public void searchByBfs() throws Search.NoSuchPathException {
        if (_walk == true) {
            return;
        }

        Search s = new Bfs(_board);
        Node p = s.run();

        setPath(p);
        next();
    }

    public void searchByBidir() throws Search.NoSuchPathException {
        if (_walk == true) {
            return;
        }

        Search s = new BidirecionalBfs(_board);
        Node p = s.run();

        setPath(p);
        next();
    }

    public void searchByIter() throws Search.NoSuchPathException {
        if (_walk == true) {
            return;
        }

        Search s = new IterDfs(_board);
        Node p = s.run();

        setPath(p);
        next();
    }

    //Overload the method
    public void searchByIter(int limit) throws Search.NoSuchPathException {
        if (_walk == true) {
            return;
        }

        Search s = new IterDfs(_board, limit);
        Node p = s.run();

        setPath(p);
        next();
    }

    public void searchByLim(int lim) throws Search.NoSuchPathException {
        if (_walk == true) {
            return;
        }

        Search s = new LimDfs(_board, lim);
        Node p = s.run();

        setPath(p);
        next();
    }

    public boolean isWalk() {
        return _walk;
    }

    public void next() {
        if (_path == null) {
            return;
        }
        if (_walk == false) {
            _walk = true;
        }
        if (_path.isEmpty()) {
            _walk = false;
            _path = null;
            return;
        }

        if (_current != null) {
            _board.set(_current.getRow(), _current.getCol(), Node.EMPTY);
        }

        _current = _path.pop();
        _board.set(_current.getRow(), _current.getCol(), Node.PATH);
    }

    public int getCost() {
        return _cost;
    }

    public static void main(String[] args) {
        Board b = new Board(10);
        Robo robo = new Robo(b);
        try {
            robo.searchByAs();
            while (robo.isWalk()) {
                robo.next();
            }
        } catch (Exception e) {
            System.out.println("Caminho nao encontrado");
        }

        System.out.println("" + b);
        System.out.println("" + robo.getCost());
    }

}
