import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { LabelService } from '../service/label.service';
import { ILabel } from '../label.model';
import { LabelFormService } from './label-form.service';

import { LabelUpdateComponent } from './label-update.component';

describe('Label Management Update Component', () => {
  let comp: LabelUpdateComponent;
  let fixture: ComponentFixture<LabelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let labelFormService: LabelFormService;
  let labelService: LabelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [LabelUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(LabelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LabelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    labelFormService = TestBed.inject(LabelFormService);
    labelService = TestBed.inject(LabelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const label: ILabel = { id: 7351 };

      activatedRoute.data = of({ label });
      comp.ngOnInit();

      expect(comp.label).toEqual(label);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILabel>>();
      const label = { id: 4199 };
      jest.spyOn(labelFormService, 'getLabel').mockReturnValue(label);
      jest.spyOn(labelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ label });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: label }));
      saveSubject.complete();

      // THEN
      expect(labelFormService.getLabel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(labelService.update).toHaveBeenCalledWith(expect.objectContaining(label));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILabel>>();
      const label = { id: 4199 };
      jest.spyOn(labelFormService, 'getLabel').mockReturnValue({ id: null });
      jest.spyOn(labelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ label: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: label }));
      saveSubject.complete();

      // THEN
      expect(labelFormService.getLabel).toHaveBeenCalled();
      expect(labelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILabel>>();
      const label = { id: 4199 };
      jest.spyOn(labelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ label });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(labelService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
