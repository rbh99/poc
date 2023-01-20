
github
r_bogdan@y
Parola11/
rbh99






https://microservices.io/patterns/data/database-per-service.html (https://microservices.io/patterns/data/database-per-service.html)



# How would you package this for deployment?

jar file with config directory for overridable configuration data. or a virtual image, if using docker

# How and where would you deploy this app?

docker container?

Deployment patterns

## Multiple service instances per host (Run multiple instances of different services on a host (Physical or Virtual machine), either same or diff jvms)
### benefits
 - More efficient resource utilization than the Service Instance per host pattern

### drawbacks
- Risk of conflicting resource requirements
- Risk of conflicting dependency versions
- Difficult to limit the resources consumed by a service instance
- If multiple services instances are deployed in the same process then its difficult to monitor the resource consumption of each service instance. Its also impossible to isolate each instance

## Service instance per host (Deploy each single service instance on its own host)
### benefits
+ Services instances are isolated from one another
+ There is no possibility of conflicting resource requirements or dependency versions
+ A service instance can only consume at most the resources of a single host
+ Its straightforward to monitor, manage, and re-deploy each service instance

### drawbacks
- resource utilization efficiency

## Service instance per VM (Package the service as a virtual machine image and deploy each service instance as a separate VM)
### benefits
+ Its straightforward to scale the service by increasing the number of instances. Amazon Autoscaling Groups can even do this automatically based on load.
+ The VM encapsulates the details of the technology used to build the service. All services are, for example, started and stopped in exactly the same way.
+ Each service instance is isolated
+ A VM imposes limits on the CPU and memory consumed by a service instance
+ IaaS solutions such as AWS provide a mature and feature rich infrastructure for deploying and managing virtual machines. For example,
+ Elastic Load Balancer -
+ Autoscaling groups


### drawbacks
- Building a VM image is slow and time consuming

Related patterns
This pattern is a refinement of the Single Service per Host pattern
The Service Instance per Container pattern is an alternative solution
The Serverless deployment pattern is an alternative solution.

## Service instance per Container (Package the service as a (Docker) container image and deploy each service instance as a container). refinement of service per host
### benefits
+ It is straightforward to scale up and down a service by changing the number of container instances.
+ The container encapsulates the details of the technology used to build the service. All services are, for example, started and stopped in exactly the same way.
+ Each service instance is isolated
+ A container imposes limits on the CPU and memory consumed by a service instance
+ Containers are extremely fast to build and start. For example, it’s 100x faster to package an application as a Docker container than it is to package it as an AMI. + Docker containers also start much faster than a VM since only the application process starts rather than an entire OS.


### drawbacks
- The infrastructure for deploying containers is not as rich as the infrastructure for deploying virtual machines.


## Serverless deployment (Use a deployment infrastructure that hides any concept of servers (i.e. reserved or preallocated resources)- physical or virtual hosts, or containers. The infrastructure takes your service’s code and runs it. You are charged for each request based on the resources consumed.). To deploy your service using this approach, you package the code (e.g. as a ZIP file), upload it to the deployment infrastructure and describe the desired performance characteristics.

The deployment infrastructure is a utility operated by a public cloud provider. It typically uses either containers or virtual machines to isolate the services. However, these details are hidden from you. Neither you nor anyone else in your organization is responsible for managing any low-level infrastructure such as operating systems, virtual machines, etc.

There are a few different serverless deployment environments:

AWS Lambda
Google Cloud Functions
Azure Functions

They offer similar functionality but AWS Lambda has the richest feature set. An AWS Lambda function is a stateless component that is invoked to handle events. To create an AWS Lambda function you package your the NodeJS, Java or Python code for your service in a ZIP file, and upload it to AWS Lambda. You also specify the name of function that handles events as well as resource limits.

When an event occurs, AWS Lambda finds an idle instance of your function, launching one if none are available and invokes the handler function. AWS Lambda runs enough instances of your function to handle the load. Under the covers, it uses containers to isolate each instance of a lambda function. As you might expect, AWS Lambda runs the containers on EC2 instances.

There are four ways to invoke a lambda function. One option is to configure your lambda function to be invoked in response to an event generated by an AWS service such as S3, DynamoDB or Kinesis. Examples of events include the following:

an object being created in a S3 bucket
an item is created, updated or deleted in a DynamoDB table
a message is available to read from a Kinesis stream
an email being received via the Simple email service.
Another way to invoke a lambda function is to configure the AWS Lambda Gateway to route HTTP requests to your lambda. AWS Gateway transforms an HTTP request into an event object, invokes the lambda function, and generates a HTTP response from the lambda function’s result.

You can also explicitly invoke your lambda function using the AWS Lambda Web Service API. Your application that invokes the lambda function supplies a JSON object, which is passed to the lambda function. The web service call returns the value returned by the lambda.

The fourth way to invoke a lambda function is periodically using a cron-like mechanism. You can, for example, tell AWS to invoke you lambda function every five minutes.

The cost of each invocation is a function of the duration of the invocation, which is measured in 100 millisecond increments, and the memory consumed.


### benefits
+ It eliminates the need to spend time on the undifferentiated heavy lifting of managing low-level infrastructure. Instead, you can focus on your code.
+ The serverless deployment infrastructure is extremely elastic. It automatically scales your services to handle the load.
+ You pay for each request rather than provisioning what might be under utilized virtual machines or containers.


### drawbacks
- Significant limitation and constraints - A serverless deployment environment typically has far more constraints that a VM-based or Container-based infrastructure. For example, AWS Lambda only supports a few languages. It is only suitable for deploying stateless applications that run in response to a request. You cannot deploy a long running stateful application such as a database or message broker.
- Limited “input sources” - lambdas can only respond to requests from a limited set of input sources. AWS Lambda is not intended to run services that, for example, subscribe to a message broker such as RabbitMQ.
- Applications must startup quickly - serverless deployment is not a good fit your service takes a long time to start
- Risk of high latency - the time it takes for the infrastructure to provision an instance of your function and for the function to initialize might result in significant latency. Moreover, a serverless deployment infrastructure can only react to increases in load. You cannot proactively pre-provision capacity. As a result, your application might initially exhibit high latency when there are sudden, massive spikes in load.

The deployment infrastructure will internally deploy your application using one of the other patterns. It will most likely use Service Service per Host pattern.


## Service deployment platform (Use a deployment platform, which is automated infrastructure for application deployment. It provides a service abstraction, which is a named, set of highly available (e.g. load balanced) service instances. 
Ex: 
Docker orchestration frameworks including Docker swarm mode and Kubernetes
Serverless platforms such as AWS Lambda
PaaS including Cloud Foundry and AWS Elastic Beanstalk




# How can you tell that the app is up and running?

A service has an health check API endpoint (e.g. HTTP /health) that returns the health of the service. 
The API endpoint handler performs various checks, such as the status of the connections to the infrastructure services used by the service instance
the status of the host, e.g. disk space
application specific logic

A health check client - a monitoring service, service registry or load balancer - periodically invokes the endpoint to check the health of the service instance.
 
### drawbacks
 The health check might not sufficiently comprehensive or the service instance might fail between health checks and so requests might still be routed to a failed service instance
Service registry - the service registry invokes the health check endpoint


# How would you configure the app as it goes from dev, to qa and finally to production?
Spring Profiles with different application properties. ... or, even externalize configuration and use a configuration server to retrieve it.
containerization ...


# How would you insulate the app from a downstream API if it had one.

The downstream services are the ones that consume the upstream service. So the front-end is downstream to the back-end because it depends on the back-end. 
Solution use an **API gateway** that is the single entry point for all clients. A variation of this pattern is the Backends for frontends pattern. 
It defines a separate API gateway for each kind of client (Web / Mobile)

Using an API gateway has the following benefits:

Insulates the clients from how the application is partitioned into microservices
Insulates the clients from the problem of determining the locations of service instances
Provides the optimal API for each client
Reduces the number of requests/roundtrips. For example, the API gateway enables clients to retrieve data from multiple services with a single round-trip. Fewer requests also means less overhead and improves the user experience. An API gateway is essential for mobile applications.
Simplifies the client by moving logic for calling multiple services from the client to API gateway
Translates from a “standard” public web-friendly API protocol to whatever protocols are used internally

The API gateway pattern has some drawbacks:

Increased complexity - the API gateway is yet another moving part that must be developed, deployed and managed
Increased response time due to the additional network hop through the API gateway - however, for most applications the cost of an extra roundtrip is insignificant.

Issues:

How implement the API gateway? An event-driven/reactive approach is best if it must scale to scale to handle high loads. On the JVM, NIO-based libraries such as Netty, Spring Reactor, etc. make sense. NodeJS is another option.

Related patterns
The Microservice architecture pattern creates the need for this pattern.
The API gateway must use either the Client-side Discovery pattern or Server-side Discovery pattern to route requests to available service instances.
The API Gateway may authenticate the user and pass an Access Token containing information about the user to the services
An API Gateway will use a Circuit Breaker to invoke services
An API gateway often implements the API Composition pattern


# Scalability

## Web Servers:

Run your app on multiple servers, behind a load balancer. Use AWS Elastic Beanstalk or roll your own solution with EC2 + Autoscaling Groups + ELB.

You mentioned a concern about "flooding" of the load balancer, but if you use Amazon's Elastic Load Balancer service it will scale automatically to handle whatever traffic you get so that you don't need to worry about this concern.

## Database Servers:

Move your database to **RDS and enable multi-az fail-over**. This will create a hot-standby server that your database will automatically fail-over to if there are issues with your primary server. Optionally add read replicas to scale-out your database capacity. (Amazon Relational Database Service is a distributed relational database service by Amazon Web Services. It is a web service running "in the cloud" designed to simplify the setup, operation, and scaling of a relational database for use in applications.)

Start caching your database queries in Redis if you aren't already. There are plugins out there to do this with Hibernate fairly easily. This will take a huge load off your database servers if your app performs the same queries regularly. Use AWS ElastiCache or RedisLabs for your Redis server(s).

## Images:

Stop storing your image files on your web servers! That creates lots of scalability issues. Move those to S3 and serve them directly from S3. S3 gives you unlimited storage space, automated backups, and the ability to serve the images directly from S3 which reduces the load on your web servers.

## Deployments:

There are so many solutions here that it just becomes a question about which method someone prefers. If you use Elastic Beanstalk then it provides a solution for deployments. If you don't use EB, then there are hundreds of solutions to pick from. I'd recommend designing your environment first, then choosing an automated deployment solution that will work with the environment you have designed.

## Backups:

If you do this right you shouldn't have much on your web servers to backup. With Elastic Beanstalk all you will need in order to rebuild your web servers is the code and configuration files you have checked into Git. If you end up having to backup EC2 servers you will want to look into EBS snapshots.

For database backups, RDS will perform a daily backup automatically. If you want backups outside RDS you can schedule those yourself using pg_dump with a cron job.

For images, you can enable S3 versioning and multi-region replication.

## CDN:

You didn't mention this, but you should look into a CDN. This will allow your application to be served faster while reducing the load on your servers. AWS provides the CloudFront CDN, and I would also recommend looking at CloudFlare.
(CDN is short for content delivery network. A content delivery network (CDN) is a system of distributed servers (network) that deliver pages and other web content to a user, based on the geographic locations of the user, the origin of the webpage and the content delivery server.)

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

## More specifically, there are three basic measures of performance:

- Response time: This is the most widely used metric of performance and it is simply a direct measure of how long it takes to process a request.
- Throughput: A straightforward count of the number of requests that the application can process within a defined time interval. For Web applications, a count of page impressions or requests per second is often used as a measure of throughput.
- System availability: Usually expressed as a percentage of application running time minus the time the application can't be accessed by users. This is an indispensable metric, because both response time nor throughput are zero when the system is unavailable.
Performance can also be defined by resource requests and by measuring resource re-quests in relation to throughput. This becomes a key metric in the context of resource planning. For instance, you may need to know which resources are needed to achieve full application scaling, which is a matter of knowing the frequency of requests for each resource type.

## The ability to overcome performance limits by adding resources is defined as scalability.
- Vertical Scaling or scaling up a single node: Adding hardware resources to an existing node. This is often used in virtualized environments where it's possible to expand system resources dynamically. It has the clear advantage that it requires no changes to the application architecture.
- Horizontal Scaling or scaling out by adding nodes: Dispatching requests among additional nodes is the preferred scaling solution when it is easier to add additional nodes than increasing the resources of a single node. This is, especially true in cloud-based environments (see figure below). Eventually we have to scale horizontally anyways as a single machine always has limited capacity.


