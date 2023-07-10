/*
 * Copyright 2020 Xiaomi
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.xiaomi.mone.log.manager.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author milog
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogStoreParam {
    private Long id;
    private Long spaceId;
    private String logstoreName;
    private Integer storePeriod;
    private Integer shardCnt;
    private String keyList;
    private String columnTypeList;
    /**
     * 1. 服务应用日志
     */
    private Integer logType;
    private Boolean isMatrixApp;
    private String esIndex;
    private String machineRoom;
    /**
     * mq资源ID
     */
    private Long mqResourceId;
    /**
     * es资源ID
     */
    private Long esResourceId;

    public boolean isMatrixAppStore() {
        return this.isMatrixApp == null ? false : this.isMatrixApp;
    }
}
