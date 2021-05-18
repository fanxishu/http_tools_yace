FROM mcr.microsoft.com/java/jdk:8-zulu-centos

ENV LANG C.UTF-8

RUN useradd -rm -d /home/baoc -s /bin/bash -g root -G root -u 1001 baoc && mkdir /usr/local/nldata && chown -hR baoc:root /usr/local/nldata
USER 1001
WORKDIR /usr/local/nldata
RUN mkdir -p /usr/local/nldata/config /usr/local/nldata/data /usr/local/nldata/logs/dag-controller
ADD serviceKeeper-*.RELEASE.jar /usr/local/nldata
ADD start.sh /usr/local/nldata
ADD application-prod.yml /usr/local/nldata/config/application-prod.yml
ADD DATA_MAP.csv /usr/local/nldata/data/DATA_MAP.csv
ADD open_gray_route_cfg.csv /usr/local/nldata/data/open_gray_route_cfg.csv
ENTRYPOINT ["sh","/usr/local/nldata/start.sh"]
EXPOSE 8090