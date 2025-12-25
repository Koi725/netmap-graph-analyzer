# NetMap - Network Graph Analyzer

A network topology analyzer built to learn graph algorithms, regex pattern matching, and object serialization in Java.

## What It Does

NetMap helps you model computer networks as graphs, validate network data with regex, and save/load network configurations. Think of it like a simplified network monitoring tool that shows you how data flows between servers, routers, and clients.

I built this to understand:

- How graph algorithms work for finding paths in networks
- Why regex is essential for validating IPs, emails, and network data
- How serialization lets you save complex object structures to disk

## Quick Start

```bash
# Run everything
chmod +x build.sh
./build.sh all
```

## What You'll See

**Part 1: Regex Validation**
Tests IP addresses, emails, MAC addresses, and extracts IPs from log text:

- Valid: `192.168.1.1` ✓
- Invalid: `256.1.1.1` ✗
- Extracts IPs from: "Server 192.168.1.100 connected from 10.0.0.50"

**Part 2: Graph Operations**
Builds a network with 10 nodes (servers, routers, clients, firewall, switch):

- BFS shortest path algorithm finds routes between nodes
- DFS connectivity check verifies if nodes can reach each other
- Shows network topology and connections

**Part 3: Serialization**
Saves entire network to disk and loads it back:

- Binary serialization (.ser files)
- JSON export for human-readable format
- Verifies data integrity after loading

**Part 4: Statistics**
Shows network composition and structure

## Requirements (What Professor Asked For)

### 1. Regex

Pattern matching for network validation:

- **IPv4 addresses**: `^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}...`
- **MAC addresses**: `^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$`
- **Email addresses**: Standard email regex
- **Extraction**: Pull IPs from log text

Why regex? Network data needs validation. You can't just trust user input. Regex ensures IP addresses are actually valid before adding them to the network.

### 2. Serialization

Save and load network graphs:

- `ObjectOutputStream` for binary serialization
- Implements `Serializable` interface on Node, Edge, NetworkGraph
- Custom JSON exporter for readable output

Why serialization? Networks take time to build. Serialization lets you save your work and resume later without rebuilding everything.

### 3. Graphs

Network topology as a directed graph:

- **Adjacency list** representation
- **BFS** (Breadth-First Search) for shortest path
- **DFS** (Depth-First Search) for connectivity checks
- Nodes = network devices, Edges = connections

Why graphs? Networks ARE graphs. Servers connect to routers, routers to switches, switches to clients. Graph algorithms help you find the fastest route or check if two devices can communicate.

## Project Structure

```
src/main/java/com/netmap/
├── Application.java              # Main demo
├── core/
│   ├── domain/                  # Node, Edge, enums
│   └── service/                 # NetworkService
├── graph/                       # Graph algorithms (BFS, DFS)
├── regex/                       # Pattern validators
├── serialization/               # Save/load functionality
└── util/                        # Generators, logger, formatter
```

## Technical Details

### Regex Patterns

All patterns use `Pattern.compile()` for efficiency:

- IPv4 checks each octet is 0-255
- MAC accepts both `:` and `-` separators
- Email validates username@domain.extension format

Extraction uses `Matcher.find()` in a loop to get all matches from text.

### Graph Algorithms

**BFS Shortest Path:**

```
Queue-based exploration
Visit nodes level by level
Guaranteed shortest path in unweighted graph
O(V + E) time complexity
```

**DFS Connectivity:**

```
Recursive exploration
Marks visited nodes
Detects if path exists
O(V + E) time complexity
```

### Serialization Process

1. Graph implements `Serializable`
2. All contained objects (Node, Edge) also implement `Serializable`
3. `ObjectOutputStream` writes object tree to file
4. `ObjectInputStream` reconstructs objects from file
5. Uses `serialVersionUID` for version control

## Building Manually

If script doesn't work:

```bash
# Compile
find src/main/java -name "*.java" > sources.txt
javac -d build/classes -sourcepath src/main/java @sources.txt

# Run
java -cp build/classes com.netmap.Application
```

## Testing

Tests cover:

- Regex validation correctness
- Graph path finding accuracy
- Serialization data integrity

Run tests:

```bash
./build.sh test
```

## What I Learned

**Regex is powerful but complex.** Writing the IPv4 pattern taught me about character classes, quantifiers, and grouping. The pattern looks cryptic but it's just checking each octet individually.

**Graphs are everywhere.** Once you see networks as graphs, you realize social networks, road maps, and dependencies are all graphs too. BFS and DFS are fundamental algorithms you use in many places.

**Serialization has gotchas.** Everything in your object tree must be Serializable. If you forget one field or nested object, you get runtime errors. Also, changing class structure breaks old serialized files unless you manage `serialVersionUID`.

## Design Decisions

**Why adjacency list over adjacency matrix?**
Networks are sparse - not every device connects to every other device. Adjacency list uses O(V + E) space instead of O(V²), much better for sparse graphs.

**Why BFS for shortest path?**
BFS explores level by level, guaranteeing the shortest path in unweighted graphs. For weighted graphs (like bandwidth-aware routing) you'd need Dijkstra's algorithm.

**Why Builder pattern?**
Node and Edge have many fields. Builder makes construction readable and ensures required fields are set:

```java
Node.builder()
    .id("server1")
    .name("Web Server")
    .ipAddress("192.168.1.100")
    .type(NodeType.SERVER)
    .build();
```

## Files Generated

- `data/*.ser` - Serialized network files
- `data/*.json` - JSON exports
- `logs.txt` - Detailed execution logs

## Performance

Tested with 100 nodes, 200 edges:

- BFS shortest path: ~5ms
- DFS connectivity: ~3ms
- Serialization: ~50ms
- Deserialization: ~30ms

## Future Ideas

- Dijkstra's algorithm for weighted shortest paths
- Minimum spanning tree for optimal network design
- Cycle detection for redundancy analysis
- Network flow algorithms for bandwidth optimization

## Real-World Applications

This project demonstrates concepts used in:

- Network routing protocols (OSPF, BGP use graph algorithms)
- Configuration management (save/load network configs)
- Network monitoring (validate device data)
- Topology visualization

## License

MIT - for learning

## Acknowledgments

Built for Data Structures and Programming course at University of Aveiro. Professor wanted us to understand regex patterns, graph algorithms, and serialization mechanisms. These are fundamental CS concepts used in networking, databases, compilers, and more.
