# Read Me First
The following was discovered as part of building this project
-- todo --jasper report updation and generation documentation

# Getting Started
run the server this just a springboot application
freemarker report generator can be accessed in 
localhost:8080/fm/export?template={Hello}    -- {Hello}  -your ftl template file
for the hello ftl template you should pass
    { 
        "user":"jawath",
        "greeting":"hello  Good afternoon "
        "nested": { "xyz":"this is nested property"} 
    }
interesting article
https://labs.consol.de/development/2018/03/26/dynamic-and-complex-configurations-with-freemarker.html
