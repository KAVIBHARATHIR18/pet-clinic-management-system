import {
  ChangeDetectorRef,
  Component,
  OnInit
} from '@angular/core';

import { FormsModule } from '@angular/forms';
import { PetClinic } from './services/pet-clinic';

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {

  owners: any[] = [];
  pets: any[] = [];
  vets: any[] = [];
  appointments: any[] = [];
  aboveAverageVets: any[] = [];

  selectedPetId: number | null = null;
  selectedVetId: number | null = null;

  feeVetId: number | null = null;
  consultationFee: number | null = null;
  feeError = '';

  appointmentDate = '';
  appointmentTime = '';
  reason = '';

  bookingMessage = '';
  bookingError = '';

  constructor(
    private petClinic: PetClinic,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    console.log('Pet Clinic Angular application started');

    this.loadOwners();
    this.loadPets();
    this.loadVets();
    this.loadAppointments();
    this.loadAboveAverageVets();
  }

  loadOwners(): void {

    this.petClinic.getOwners().subscribe({

      next: (data) => {

        console.log('Owners received:', data);

        this.owners = data;

        this.changeDetectorRef.detectChanges();
      },

      error: (error) => {

        console.error('Error loading owners:', error);

      }

    });
  }

  loadPets(): void {

    this.petClinic.getPets().subscribe({

      next: (data) => {

        console.log('Pets received:', data);

        this.pets = data;

        this.changeDetectorRef.detectChanges();
      },

      error: (error) => {

        console.error('Error loading pets:', error);

      }

    });
  }

  loadVets(): void {

    this.petClinic.getVets().subscribe({

      next: (data) => {

        console.log('Vets received:', data);

        this.vets = data;

        this.changeDetectorRef.detectChanges();
      },

      error: (error) => {

        console.error('Error loading vets:', error);

      }

    });
  }

  loadAppointments(): void {

    this.petClinic.getAppointmentDetails().subscribe({

      next: (data) => {

        console.log('Appointments received:', data);

        this.appointments = data;

        this.changeDetectorRef.detectChanges();
      },

      error: (error) => {

        console.error('Error loading appointments:', error);

      }

    });
  }

  loadAboveAverageVets(): void {

    this.petClinic.getVetsAboveAverage().subscribe({

      next: (data) => {

        console.log('Above-average vets received:', data);

        this.aboveAverageVets = data;

        this.changeDetectorRef.detectChanges();
      },

      error: (error) => {

        console.error(
          'Error loading above-average vets:',
          error
        );

      }

    });
  }

  calculateFee(): void {

    this.consultationFee = null;
    this.feeError = '';

    if (this.feeVetId === null) {

      this.feeError =
        'Please select a veterinarian.';

      return;
    }

    this.petClinic
      .getConsultationFee(this.feeVetId)
      .subscribe({

        next: (fee) => {

          console.log(
            'Consultation fee received:',
            fee
          );

          this.consultationFee = fee;

          this.changeDetectorRef.detectChanges();
        },

        error: (error) => {

          console.error(
            'Error calculating consultation fee:',
            error
          );

          this.feeError =
            'Unable to calculate consultation fee.';

          this.changeDetectorRef.detectChanges();
        }

      });
  }

  bookAppointment(): void {

    console.log(
      'Book Appointment button clicked'
    );

    this.bookingMessage = '';
    this.bookingError = '';

    console.log(
      'Pet ID:',
      this.selectedPetId
    );

    console.log(
      'Vet ID:',
      this.selectedVetId
    );

    console.log(
      'Date:',
      this.appointmentDate
    );

    console.log(
      'Time:',
      this.appointmentTime
    );

    console.log(
      'Reason:',
      this.reason
    );

    if (
      this.selectedPetId === null ||
      this.selectedVetId === null ||
      !this.appointmentDate ||
      !this.appointmentTime ||
      !this.reason.trim()
    ) {

      this.bookingError =
        'Please fill in all appointment details.';

      return;
    }

    const appointment = {

      petId: this.selectedPetId,

      vetId: this.selectedVetId,

      appointmentDate:
        this.appointmentDate,

      appointmentTime:
        this.appointmentTime + ':00',

      reason:
        this.reason.trim()

    };

    console.log(
      'Sending appointment:',
      appointment
    );

    this.petClinic
      .bookAppointment(appointment)
      .subscribe({

        next: (response) => {

          console.log(
            'Booking successful:',
            response
          );

          this.bookingMessage =
            'Appointment booked successfully!';

          this.bookingError = '';

          this.clearForm();

          this.loadAppointments();

          this.loadAboveAverageVets();

          this.changeDetectorRef.detectChanges();
        },

        error: (error) => {

          console.error(
            'Booking error:',
            error
          );

          this.bookingMessage = '';

          if (error.status === 0) {

            this.bookingError =
              'Cannot connect to the Spring Boot server. Make sure the backend is running on port 8081.';

          } else if (error.status === 400) {

            this.bookingError =
              'Invalid appointment details. Please check the selected values.';

          } else if (error.status === 500) {

            this.bookingError =
              'Server error while booking appointment. Check the Spring Boot console.';

          } else {

            this.bookingError =
              'Failed to book appointment. HTTP Status: ' +
              error.status;

          }

          this.changeDetectorRef.detectChanges();
        }

      });
  }

  clearForm(): void {

    this.selectedPetId = null;

    this.selectedVetId = null;

    this.appointmentDate = '';

    this.appointmentTime = '';

    this.reason = '';
  }

  scrollToSection(sectionId: string): void {

    const section =
      document.getElementById(sectionId);

    if (section) {

      section.scrollIntoView({

        behavior: 'smooth',

        block: 'start'

      });

    }
  }
}