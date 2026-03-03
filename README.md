# TriageMate
TriageMate is an Android-based AI crash/log triage assistant built with Jetpack Compose.
It converts raw crash logs and stack traces into structured, actionable triage reports.

## Current Features (Week 1)
- Paste crash logs or stack traces
- Structured triage report output
- Severity + confidence classification
- Suggested debugging steps
- Copy-to-ticket formatting
- Mock analyzer (LLM integration coming in Week 4)

## Tech Stack
- Kotlin
- Jetpack Compose
- MVVM architecture
- Kotlinx Serialization

## Project Structure
TriageMate/
├── app/
├── examples/
├── prompts/
├── docs/

## Roadmap
- [ ] LLM integration (Gemini)
- [ ] Known issues database
- [ ] Tool/function calling
- [ ] RAG with documentation grounding
- [ ] Evaluation harness
---
Built as part of AI Engineer upskilling journey.