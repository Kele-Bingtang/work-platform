import type { InjectionKey, ComputedRef } from "vue";

interface ConfigGlobal {
  size: ComputedRef<"default" | "small" | "large">;
}

export const INSTALLER_SYMBOL = Symbol("INSTALLER_SYMBOL");
export const ConfigGlobalKey: InjectionKey<ConfigGlobal> = Symbol("ConfigGlobal");
export const RefreshKey: InjectionKey<(value?: boolean) => boolean> = Symbol("Refresh");
