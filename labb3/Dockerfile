# Use a base image with x86_64 architecture
FROM ubuntu:20.04

# Install necessary libraries and qemu-user for x86-64 emulation
RUN dpkg --add-architecture amd64 && \
    apt-get update && \
    apt-get install -y libc6:amd64 libstdc++6:amd64 qemu-user

# Copy the flowgen executable into the container
COPY ./flowgen /usr/local/bin/flowgen

# Set execute permissions for the flowgen executable
RUN chmod +x /usr/local/bin/flowgen

# Set the working directory (optional)
WORKDIR /workspace

# This command will keep the container running, so you can attach to it later
CMD ["tail", "-f", "/dev/null"]
