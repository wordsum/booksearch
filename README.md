# WordSum Story Search

A search of a complete story as defined by an agent.

### TODO:
- Rewrite all search operations using Kt-search
- Configure the Index
- Simple Recommendation Engine
- Analyzer Investigation
- Create a way dynamically score fields using an LLM and Langchain (prompts vs embeddings)
  - Configure local env with conda.
  - Quantum Falcon LLM (Amazon)

### Data Dictionary
- id: The unique document id.
- name: The title of the story.
- genre: The classification of the story according of the historical catagories like Romance, Science Fiction.
- type: A classification that is defined by word count and arrange of words like short story, flash fiction.
- wordCount: The amount of words in the story.
- summary: A brief explanation of the plot and characters of the story.

