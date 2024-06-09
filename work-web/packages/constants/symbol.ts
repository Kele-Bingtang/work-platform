import type { InjectionKey, ComputedRef, ShallowRef, Ref } from "vue";

interface ConfigGlobal {
  size: ComputedRef<"default" | "small" | "large">;
}

export interface WebSocketStore {
  websocket: ShallowRef<Nullable<WebSocket>>;
  status: Ref<string>;
  websocketUrl: Ref<string>;
  connect: (url: string) => void;
  disconnect: () => void;
  sendMessage: (data: any) => void;
}

export const INSTALLER_SYMBOL = Symbol("INSTALLER_SYMBOL");
export const ConfigGlobalKey: InjectionKey<ConfigGlobal> = Symbol("ConfigGlobal");
export const RefreshKey: InjectionKey<(value?: boolean) => boolean> = Symbol("Refresh");
export const WebSocketKey: InjectionKey<WebSocketStore> = Symbol("WebSocket");
