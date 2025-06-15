FROM openjdk
COPY sample.java .
RUN javac sample.java
CMD ["java", "sample"]
