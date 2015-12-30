#include <cstdio>
#include <cstdlib>
#include <iostream>
#include <stdexcept>
#include <vector>

struct Input {
    unsigned letter_count;
    std::vector<std::vector<unsigned> > p; // přijímaná slova
    std::vector<std::vector<unsigned> > n; // nepřijímaná slova

    Input(): letter_count(0) {}
};

const int undefined_transition = -1;

struct Node {
    bool is_terminal;
    std::vector<int> transitions;

    explicit Node(unsigned transition_count)
      : is_terminal(false),
        transitions(transition_count, undefined_transition) {
    }
};

struct Graph {
    std::vector<Node> nodes;

    Graph(unsigned node_count, unsigned transition_count)
      : nodes(node_count, Node(transition_count)) {
    }
};

bool check(const Input &input, Graph &g) {
    for (unsigned node_pos = 0; node_pos < g.nodes.size(); ++node_pos) {
        g.nodes[node_pos].is_terminal = false;
    }
    // projdeme slova, která mají být přijímána
    for (unsigned i = 0; i < input.p.size(); ++i) {
        const std::vector<unsigned> &word = input.p[i];

        int node_pos = 0;
        bool skip = false;
        for (unsigned wp = 0; wp < word.size(); ++wp) {
            const unsigned letter = word[wp];
            node_pos = g.nodes[node_pos].transitions[letter];
            if (node_pos == undefined_transition) {
                // došli jsme v grafu někam, kde to ještě nemáme vygenerované
                // - toto slovo přeskočíme
                skip = true;
                break;
            }
        }
        if (skip) {
            continue;
        }
        g.nodes[node_pos].is_terminal = true;
    }
    // projdeme slova, která nemají být přijímána
    for (unsigned i = 0; i < input.n.size(); ++i) {
        const std::vector<unsigned> &word = input.n[i];

        int node_pos = 0;
        bool skip = false;
        for (unsigned wp = 0; wp < word.size(); ++wp) {
            const unsigned letter = word[wp];
            node_pos = g.nodes[node_pos].transitions[letter];
            if (node_pos == undefined_transition) {
                // došli jsme v grafu někam, kde to ještě nemáme vygenerované
                // - toto slovo přeskočíme
                skip = true;
                break;
            }
        }
        if (skip) {
            continue;
        }
        if (g.nodes[node_pos].is_terminal) {
            // toto slovo nemá být přijímáno, ale podle grafu je přijímáno,
            // takže tento graf není správný
            return false;
        }
    }
    // pokud vše prošlo, předpokládáme, že graf je
    // správný (automat přijímá co má a nepřijímá co nemá)
    return true;
}

bool dfa_exists_r(const Input &input, Graph &g, unsigned node_pos, unsigned trans_pos) {
    if (node_pos == g.nodes.size()) {
        return check(input, g);
    }
    if (trans_pos == input.letter_count) {
        return dfa_exists_r(input, g, node_pos + 1, 0);
    }
    if (!check(input, g)) {
        return false;
    }
    for (unsigned i = 0; i < g.nodes.size(); ++i) {
        g.nodes[node_pos].transitions[trans_pos] = i;
        if (dfa_exists_r(input, g, node_pos, trans_pos + 1)) {
            return true;
        }
    }
    g.nodes[node_pos].transitions[trans_pos] = undefined_transition;
    return false;
}

bool dfa_exists(unsigned node_count, const Input &input) {
    Graph g(node_count, input.letter_count);
    return dfa_exists_r(input, g, 0, 0);
}

std::vector<unsigned> read_word(unsigned &letter_count, std::vector<int> &map) {
    std::vector<unsigned> v;
    std::string line;
    getline(std::cin, line);
    for (unsigned k = 0; k < line.size(); ++k) {
        char c = line[k];
        if (c == '\n' || c == '\r') {
            break;
        }
        if (map.at(c) == -1) {
            map[c] = letter_count ++;
        }
        v.push_back(map[c]);
    }
    return v;
}

Input read_input() {
    Input input;
    std::vector<int> map(256, -1);
    std::string line;
    getline(std::cin, line);
    int p_count = atoi(line.c_str());
    for (int i = 0; i < p_count; ++i) {
        input.p.push_back(read_word(input.letter_count, map));
    }
    getline(std::cin, line);
    int n_count = atoi(line.c_str());
    for (int i = 0; i < n_count; ++i) {
        input.n.push_back(read_word(input.letter_count, map));
    }
    return input;
}

int main(int argc, char *argv[]) {
    try {
        Input input = read_input();
        for (int i = 1; i < 10; i++) {
            if (dfa_exists(i, input)) {
                std::cout << i << std::endl;
                break;
            }
        }
    } catch (const std::exception &e) {
        std::cerr << "Error: " << e.what() << std::endl;
        return 1;
    }
    return 0;
}

