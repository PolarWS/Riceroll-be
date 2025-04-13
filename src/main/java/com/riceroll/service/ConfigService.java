package com.riceroll.service;

import java.io.IOException;
import java.util.Map;

public interface ConfigService {
    Map<String, Object> getConfig() throws IOException;
}
