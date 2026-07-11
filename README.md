# BPMN Layout Tool

A Java-based BPMN auto-layout engine that parses BPMN XML, calculates an optimized layout, and generates BPMN DI (Diagram Interchange) information.

## Features

- Parse BPMN 2.0 XML
- Build graph model
- Detect branches and joins
- Assign levels and lanes
- Generate grid layout
- Coordinate assignment
- Orthogonal edge routing
- BPMN DI generation (In Progress)

## Technologies

- Java 17
- Maven
- IntelliJ IDEA Community
- DOM XML Parser

## Project Structure

```
src/main/java/com/tnq/bpmnlayout
├── analysis
├── graph
├── grid
├── layout
├── model
├── optimizer
├── parser
├── routing
├── util
└── writer
```

## Build

```bash
mvn clean package
```

## Run

```bash
java -jar target/bpmn-layout-tool.jar input.bpmn
```

## Current Status

🚧 Under active development.

Completed:
- Graph model
- BPMN parser
- Level assignment
- Lane assignment
- Grid generation
- Coordinate assignment

In Progress:
- Orthogonal routing
- BPMN DI Writer
- Layout optimization

## Author

Suresh Arumugam
