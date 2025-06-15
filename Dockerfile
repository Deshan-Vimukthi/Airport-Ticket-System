FROM openjdk
COPY Sample.java .
RUN javac Sample.java
CMD ["java", "Sample"]
