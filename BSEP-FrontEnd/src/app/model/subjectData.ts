import { ExtensionDTO } from '../modelDTO/extensionDTO';

export class SubjectData {
    id: string;
	commonName: string;
	surname: string;
	givenName: string;
    organization: string;
    organizationUnit: string;
    country:string;
    email:string;
    dateFrom:string;
    dateUntil:string;
    uid: string;

    extensions: Array<ExtensionDTO> = [];



}