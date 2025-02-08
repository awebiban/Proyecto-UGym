import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservaPrivadaComponent } from './reserva-privada.component';

describe('ReservaPrivadaComponent', () => {
  let component: ReservaPrivadaComponent;
  let fixture: ComponentFixture<ReservaPrivadaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservaPrivadaComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ReservaPrivadaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
