import { TurboModule, TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  add(a: number, b: number): Promise<number>;
  initPilot(token: string): void;
  generateUrl(originalUrl: string, propertyId: string): Promise<string>;
}

export default TurboModuleRegistry.get<Spec>('RTNCalculator') as Spec | null;
