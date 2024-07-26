package me.kcj.sec05.parser;

import com.google.protobuf.InvalidProtocolBufferException;
import me.kcj.models.sec05.v1.Television;
import me.kcj.sec05.V1VersionCompatibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V1Parser {
    private static final Logger log = LoggerFactory.getLogger(V1VersionCompatibility.class);

    public static void parse(byte[] bytes) throws InvalidProtocolBufferException {
        var tv = Television.parseFrom(bytes);
        log.info("brand :{}", tv.getBrand());
        log.info("year :{}", tv.getYear());
        //tv.getUnknownFields() list down all the fields that are not in proto
    }
}
