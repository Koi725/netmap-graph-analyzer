# Quick Start Guide

Get NetMap running in 2 minutes.

---

## Prerequisites

- Java JDK 11+
- Terminal (Bash/Zsh/Git Bash)

---

## Run It

```bash
# Clone repository
git clone <your-repo-url>
cd netmap-graph-analyzer

# Make executable
chmod +x build.sh

# Run everything
./build.sh all
```

---

## Expected Output

```
1. REGEX VALIDATION
- IP, email, MAC address validation
- Extract IPs from text

2. GRAPH OPERATIONS
- Build network with 10 nodes
- BFS shortest path
- DFS connectivity check

3. SERIALIZATION
- Save to data/network_backup.ser
- Export to data/network_export.json
- Load and verify

4. STATISTICS
- Node counts by type
- Connection statistics
```

---

## Commands

```bash
./build.sh compile    # Compile only
./build.sh test       # Run tests
./build.sh run        # Run demo
./build.sh all        # Everything
```

---

## Files Generated

- `data/*.ser` - Serialized networks
- `data/*.json` - JSON exports
- `logs.txt` - Execution logs

---

Done!
