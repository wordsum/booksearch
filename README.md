# WordSum Story Search

### Long Term Goals
- Train A.I. to give it agency.

### Mid Term Goals
- Train A.I. to write, edit, publish, critique fiction and non-fiction.
- Train A.I. to classify fiction vs non-fiction.

### Short Term Goals
- Train an LLM with prompts and embeddings to classify a story then find publishers for that story.
- Train an LLM to determine if a story is complete and ready for publication.

### TODO:
- Use local a LLM and Langchain to return data dictionary definitions/classifications for fiction story.
  - Configure local env with conda.
  - Test prompts with Falcon LLM
- Simple Recommendation Engine
- Analyzer Investigation

### Data Dictionary
- id: The unique document id.
- name: The title of the story.
- genre: The classification of the story according of the historical catagories like Romance, Science Fiction.
- type: A classification that is defined by word count and arrange of words like short story, flash fiction.
- wordCount: The amount of words in the story.
- summary: A brief explanation of the plot and characters of the story.

