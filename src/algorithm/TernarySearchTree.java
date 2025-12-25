package algorithm;

import java.util.ArrayList;
import java.util.List;

public class TernarySearchTree {

    private class Node {
        char c;
        boolean isEnd;
        Node left, mid, right;
    }

    private Node root;

    public void insert(String word) {
        root = insert(root, word.toLowerCase(), 0);
    }

    private Node insert(Node node, String word, int index) {
        char ch = word.charAt(index);

        if (node == null) {
            node = new Node();
            node.c = ch;
        }

        if (ch < node.c)
            node.left = insert(node.left, word, index);
        else if (ch > node.c)
            node.right = insert(node.right, word, index);
        else {
            if (index == word.length() - 1)
                node.isEnd = true;
            else
                node.mid = insert(node.mid, word, index + 1);
        }
        return node;
    }

    // 🔍 AUTO-SUGGEST
    public List<String> autoSuggest(String prefix) {
        List<String> results = new ArrayList<>();
        Node node = search(root, prefix.toLowerCase(), 0);

        if (node == null) return results;

        if (node.isEnd) results.add(prefix);
        collect(node.mid, new StringBuilder(prefix), results);
        return results;
    }

    private Node search(Node node, String word, int index) {
        if (node == null) return null;
        char ch = word.charAt(index);

        if (ch < node.c)
            return search(node.left, word, index);
        else if (ch > node.c)
            return search(node.right, word, index);
        else {
            if (index == word.length() - 1) return node;
            return search(node.mid, word, index + 1);
        }
    }

    private void collect(Node node, StringBuilder prefix, List<String> results) {
        if (node == null) return;

        collect(node.left, prefix, results);

        prefix.append(node.c);
        if (node.isEnd) results.add(prefix.toString());
        collect(node.mid, prefix, results);
        prefix.deleteCharAt(prefix.length() - 1);

        collect(node.right, prefix, results);
    }
}
