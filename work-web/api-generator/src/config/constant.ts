export const colTypeComponentForm = {
  String: ["el-input", "text"],
  Integer: ["el-input", "number"],
  Float: ["el-input", "number"],
  Double: ["el-input", "number"],
  Date: ["el-date-picker", "date"],
  DateTime: ["el-date-picker", "datetime"],
  TimeStamp: ["el-date-picker", "datetime"],
  Blob: ["el-input", "textarea"],
  Text: ["el-input", "textarea"],
};

export const colTypeComponentSearch = {
  String: ["el-input", "text"],
  Integer: ["el-input", "number"],
  Float: ["el-input", "number"],
  Double: ["el-input", "number"],
  Date: ["el-date-picker", "daterange"],
  DateTime: ["el-date-picker", "datetimerange"],
  TimeStamp: ["el-date-picker", "datetimerange"],
  Blob: ["el-input", "text"],
  Text: ["el-input", "text"],
};

export const queryFilter = ["关闭", "=", "!=", "Left Like", "Right Like", "Like", "<", ">", "<=", ">=", "Between"];
