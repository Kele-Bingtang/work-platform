import { type FormSchemaProps } from "@work/components";
import { getProp, hyphenToCamelCase, setProp } from "../helper";
import type { ComputedRef, ModelRef, defineComponent } from "vue";
import { componentMap } from "../helper/componentMap";
import { type PascalCaseComponentName } from "../interface";

export const useRenderComponent = (
  model: ModelRef<Record<string, any>, string>,
  componentProps: ComputedRef<Record<string, any>>,
  column: FormSchemaProps
) => {
  const renderComponent = () => {
    // 注册自定义的组件
    const Component = componentMap[hyphenToCamelCase(column.el) as PascalCaseComponentName] as ReturnType<
      typeof defineComponent
    >;

    return (
      <Component
        modelValue={getProp(model.value, column.prop, column.valueFormat)}
        onUpdate:modelValue={(v: any) => setProp(model.value, column.prop, v)}
        {...componentProps.value}
      ></Component>
    );
  };

  return { renderComponent };
};
