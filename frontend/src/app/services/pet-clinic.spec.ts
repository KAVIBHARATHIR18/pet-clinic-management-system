import { TestBed } from '@angular/core/testing';
import { PetClinic } from './pet-clinic';

describe('PetClinic', () => {
  let service: PetClinic;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PetClinic);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
