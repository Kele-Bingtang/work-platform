/**
 * @description 处理 ProForm 多级 prop，将多级 prop 转为对象
 * 如 prop 为 meta.title，则转为 { meta: { title: "" } }
 */
export const handleNestProp = (obj: Record<string, any>, delimiter = ".") => {
  const transformed: Record<string, any> = {};

  for (const key in obj) {
    const keys = key.split(delimiter);
    let current = transformed;

    for (const [i, subKey] of keys.entries()) {
      if (i === keys.length - 1) {
        current[subKey] = obj[key];
        break;
      }

      current[subKey] = current[subKey] || {};
      current = current[subKey];
    }
  }

  return transformed;
};
