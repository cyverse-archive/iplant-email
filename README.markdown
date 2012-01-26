iplant-email
============

Provides a backend service for a relatively simple way to send emails to users.

Configuration
-------------

By default, iplant-email will use a unauthenticated, unencrypted local SMTP server that runs on port 25. If you need to change any of these settings, the configuration file lives inside the WAR file at WEB-INF/classes/iplant-email.properties. 

Additionally, the configuration file allows you to set the "from" address for the emails that are sent from this service.


Making a request
----------------

There is only one endpoint in the iplant-email app, "/". To request that an email be sent, you need to know the template name, the 'to' email address, and the values that are to be interpolated into the email template.

Here is a sample request:

    curl -H "Content-Type:application/json" -d '{"to" : "foo@example.com", "subject" : "Example", "template" : "bettertest", "values" : {"user" : "Foo", "useremail" : "foo@example.com", "value" : "foobar"}}' http://127.0.0.1:3000/

The 'to' field is hopefully pretty self-explanatory, as is the 'subject' field. 

The 'template' field corresponds to an email template located in the WEB-INF/classes/ directory of the deployed iplant-email WAR. It is the filename of the template, minus the '.st' extension.

The values field is a map that contains values that are interpolated into corresponding fields in the email template. For instance, "Foo" is substituted into the email wherever $user$ appears.


Adding/Modifying Email Templates
--------------------------------

Email templates live in the WEB-INF/classes/ directory of the deployed WAR file. 

You can add and modify templates on the fly, iplant-email will pick up the changes without having to be restarted. 

All template files must end with a '.st' extension. The name of the template is derived from the part of the filename that comes before the '.st' extension.

The syntax for templatizing emails is available in the "Learn basic StringTemplate syntax" section on this website: http://www.antlr.org/wiki/display/ST/Five+minute+Introduction


