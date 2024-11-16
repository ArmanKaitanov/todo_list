# todo_list

This app allows you to create, update, and delete user profiles. You can also create "todo" items with titles and 
descriptions, update them, and access them. I developed this application with the assistance of ChatGPT, which provided 
me with code snippets for services, tests, mappers, and other components. However, I used these snippets mainly as 
hints and refactored many details myself. The current version of ChatGPT (version 3) sometimes struggles with 
understanding the context and specifics of my app, so it required additional adjustments on my part. Nevertheless, 
it was extremely useful for writing unit tests. For this task, I decided that the choice of database was not a 
critical factor, so I opted to use PostgreSQL.

- Was it easy to complete the task using AI?

    Yes, using ChatGPT made the process more manageable, particularly for generating unit tests and obtaining initial 
    code templates. It provided helpful suggestions and guidance throughout the development.

- How long did the task take you to complete?

    It took me approximately 8 hours to complete this task. This includes both development and integration of the 
    provided code snippets.

- Was the code ready to run after generation? What did you have to change to make it usable?

     The code generated by ChatGPT often required modifications. I had to refactor many parts to ensure they fit the 
     context of my application. For instance, I had to adjust the logic within services and mappers and implement some 
     additional functionalities. While the generated code served as a valuable foundation, it was not ready to run 
     immediately.

- Which challenges did you face during the completion of the task?

     I intentionally did not include logging and security (authentication and authorization) as these were not 
     necessary for this small application. The app includes controller, service, and repository layers and is
     sufficiently functional for its intended purpose.

- Which specific prompts did you learn as good practice to complete the task?

     A good practice was to ask for specific code blocks rather than entire solutions. For example, requesting 
     "a service for creating todo items" or "unit tests for the user service" resulted in more accurate and useful 
     responses. Breaking down tasks into smaller, more manageable prompts helped achieve better results.
