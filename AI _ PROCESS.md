# IA Usage and Development Process

This document describes how Artificial Intelligence tools were used during the development of this challenge, as requested in the assignment. The goal of this section is to provide transparency about the role of AI in the development process and clearly distinguish between AI-assisted tasks and human decision-making.

## AI Tools Used

During the development of this challenge, the following AI tools were used as support:

- **ChatGPT**: Used mainly for technical consultation, validation of ideas, and documentation improvements.
- **Gemini**: Used as a secondary source to contrast approaches and validate certain technical decisions. It's the first result of a Google search.

## Work Process with AI

The interaction with AI followed an iterative and controlled process:

1. **Project setup**  
   Initially, AI was consulted to generate a basic project setup:
    - Java 21
    - Maven as the dependency manager
    - JUnit 5 and Mockito for testing

   This helped accelerate the initial setup phase, allowing the focus to remain on design and implementation rather than boilerplate configuration.

2. **Technical Exploration and Validation**  
   AI was used to:
    - Explore configuration considerations for implementing different notification channels (Email, Push, SMS).
    - Identify realistic providers that could be used or mocked for each channel.
    - Discuss best practices for mocking external providers in a way that resembles real-world integrations.

3. **Architecture Review**  
   After defining an initial project structure, the full folder and package layout was shared with the AI to:
    - Validate alignment with Hexagonal Architecture principles.
    - Identify potential improvements or risks in the separation of concerns.
    - Confirm correct boundaries between domain, application, and infrastructure layers.

4. **Documentation Improvement**  
   AI was also used to improve documentation quality:
    - Enhancing the README to be more complete and technically clear.
    - Structuring explanations in a way that is friendly to other developers reviewing the repository.

## Prompts and Strategies Used

The main strategies when working with AI were:

- Asking **specific, scoped questions** instead of open-ended requests.
- Providing **project context and constraints** (e.g., Hexagonal Architecture, library-oriented design).
- Iteratively refining prompts when initial suggestions did not align with the intended solution.
- Treating AI output as **proposals**, not final answers.

## What AI Helped With — and What It Did Not

### AI Helped With:
- Speeding up initial project setup.
- Providing quick feedback on architectural ideas.
- Exploring technical options.
- Improving clarity and structure of documentation.

### AI Did Not:
- Design the core architecture.
- Make final technical decisions.
- Implement business logic.
- Replace human reasoning or architectural judgment.

## Conclusion
AI tools were used as **assistive instruments**, similar to advanced documentation or consultation tools. All critical design and implementation decisions were made deliberately by the developer, ensuring the final solution adheres to sound engineering principles and architectural best practices.
