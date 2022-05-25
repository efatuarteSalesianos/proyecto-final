export interface SiteResponse {
  id: number;
  name: string;
  address: string;
  city: string;
  postalCode: string;
  totalComments: number;
  rate: number;
  scaledFileUrl: string;
  liked: boolean;
}
