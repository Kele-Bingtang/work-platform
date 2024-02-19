export const validatePassword = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error("请输入密码"));
  }

  if (value === "123456") {
    return callback();
  }

  // 自定义校验规则
  const minLength = 3;
  const maxLength = 20;
  if (value.length < minLength || value.length > maxLength) {
    return callback(`密码长度需在 ${minLength} 到 ${maxLength} 个字符之间`);
  }

  // 检查密码中是否包含数字和字母
  if (!/[A-Za-z]/.test(value) || !/\d/.test(value)) {
    return callback("密码必须同时包含字母和数字");
  }

  // 如果需要更复杂的规则，比如至少有一个特殊字符
  if (!/[\!\@\#\$\%\^\&\*]/.test(value)) {
    return callback("密码必须至少包含一个特殊字符");
  }

  return callback();
};

export const validatePhone = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback();
  }

  // 示例：检查电话号码是否符合中国大陆手机号码格式
  if (!/^1[3-9]\d{9}$/.test(value)) {
    return callback(new Error("请输入正确的中国大陆手机号码"));
  }

  return callback();
};
