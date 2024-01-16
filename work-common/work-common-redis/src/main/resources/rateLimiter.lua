--获取 KEY
local key = KEYS[1]

local limit = tonumber(ARGV[1])
local currentLimit = tonumber(redis.call('get', key) or "0")

if currentLimit + 1 > limit
then return 0
else
    -- 自增长 1
    redis.call('INCRBY', key, 1)
    -- 设置过期时间
    redis.call('EXPIRE', key, 20)
    return currentLimit + 1
end