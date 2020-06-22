export class CsrDTO{
    id: number;
	commonName: string;
	surname: string;
	givenName: string;
    organization: string;
    organizationUnit: string;
    country:string;
    email:string;
    uid: string;
    extensions: string[] = [];
}